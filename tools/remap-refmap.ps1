param(
    [Parameter(Mandatory = $true)][string]$Mappings,
    [Parameter(Mandatory = $true)][string]$Jar
)

$lines = Get-Content -LiteralPath $Mappings
$classToOfficial = @{}
$officialToIntermediary = @{}

foreach ($line in $lines) {
    if (-not $line.StartsWith("c`t")) { continue }
    $parts = $line.Split("`t")
    $classToOfficial[$parts[2]] = $parts[1]
    $officialToIntermediary[$parts[1]] = $parts[2]
}
$script:classToOfficial = $classToOfficial

function Convert-Descriptor([string]$value, [hashtable]$classes) {
    return [regex]::Replace($value, 'L([^;]+);', {
        param($match)
        $name = $match.Groups[1].Value
        if ($classes.ContainsKey($name)) { return 'L' + $classes[$name] + ';' }
        return $match.Value
    })
}

$members = @{}
$globalMembers = @{}
$memberNames = @{}
$officialOwner = $null
$intermediaryOwner = $null

foreach ($line in $lines) {
    $parts = $line.Split("`t")
    if ($line.StartsWith("c`t")) {
        $officialOwner = $parts[1]
        $intermediaryOwner = $parts[2]
        continue
    }
    if ($null -eq $officialOwner -or $parts.Length -lt 5 -or $parts[0] -ne '') { continue }
    if ($parts[1] -ne 'm' -and $parts[1] -ne 'f') { continue }
    $officialDescriptor = $parts[2]
    $intermediaryDescriptor = Convert-Descriptor $officialDescriptor $officialToIntermediary
    $mapped = @($officialOwner, $parts[3], $officialDescriptor)
    $members[$intermediaryOwner + '|' + $parts[4] + '|' + $intermediaryDescriptor] = $mapped
    $memberNames[$parts[4]] = $mapped
    $globalKey = $parts[4] + '|' + $intermediaryDescriptor
    if (-not $globalMembers.ContainsKey($globalKey)) { $globalMembers[$globalKey] = $mapped }
}
$script:memberNames = $memberNames

function Convert-Selector([string]$value) {
    if ($value -match '^L([^;]+);([^(:]+)(.*)$') {
        $owner = $Matches[1]
        $name = $Matches[2]
        $suffix = $Matches[3]
        $descriptor = if ($suffix.StartsWith('(')) { $suffix } else { $suffix.Substring(1) }
        $key = $owner + '|' + $name + '|' + $descriptor
        if ($members.ContainsKey($key)) {
            $mapped = $members[$key]
            if ($suffix.StartsWith('(')) { return 'L' + $mapped[0] + ';' + $mapped[1] + $mapped[2] }
            return 'L' + $mapped[0] + ';' + $mapped[1] + ':' + $mapped[2]
        }
        if ($memberNames.ContainsKey($name)) {
            $mapped = $memberNames[$name]
            if ($suffix.StartsWith('(')) { return 'L' + $mapped[0] + ';' + $mapped[1] + $mapped[2] }
            return 'L' + $mapped[0] + ';' + $mapped[1] + ':' + $mapped[2]
        }
    }
    if ($value -match '^([^(:]+)([:(].*)$') {
        $name = $Matches[1]
        $suffix = $Matches[2]
        $descriptor = if ($suffix.StartsWith('(')) { $suffix } else { $suffix.Substring(1) }
        $key = $name + '|' + $descriptor
        if ($globalMembers.ContainsKey($key)) {
            $mapped = $globalMembers[$key]
            if ($suffix.StartsWith('(')) { return $mapped[1] + $mapped[2] }
            return $mapped[1] + ':' + $mapped[2]
        }
        if ($memberNames.ContainsKey($name)) {
            $mapped = $memberNames[$name]
            if ($suffix.StartsWith('(')) { return $mapped[1] + $mapped[2] }
            return $mapped[1] + ':' + $mapped[2]
        }
    }
    return Convert-Descriptor $value $classToOfficial
}

function Convert-Node($node) {
    if ($node -is [System.Collections.IDictionary]) {
        foreach ($key in @($node.Keys)) { $node[$key] = Convert-Node $node[$key] }
        return $node
    }
    if ($node -is [System.Collections.IList]) {
        for ($index = 0; $index -lt $node.Count; $index++) { $node[$index] = Convert-Node $node[$index] }
        return $node
    }
    if ($node -is [string]) { return Convert-Selector $node }
    return $node
}

$temporaryDirectory = Join-Path ([System.IO.Path]::GetTempPath()) ('hitboy-refmap-' + [guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $temporaryDirectory | Out-Null
try {
    Push-Location $temporaryDirectory
    try {
        & jar xf $Jar meteor-client-refmap.json
        if ($LASTEXITCODE -ne 0) { throw 'Could not extract meteor-client-refmap.json.' }
        $json = Get-Content -LiteralPath 'meteor-client-refmap.json' -Raw
        $json = [regex]::Replace($json, 'net/minecraft/class_\d+(?:\$class_\d+)*', {
            param($match)
            if ($script:classToOfficial.ContainsKey($match.Value)) { return $script:classToOfficial[$match.Value] }
            return $match.Value
        })
        $json = [regex]::Replace($json, '(?:method|field)_\d+', {
            param($match)
            if ($script:memberNames.ContainsKey($match.Value)) { return $script:memberNames[$match.Value][1] }
            return $match.Value
        })
        Set-Content -LiteralPath 'meteor-client-refmap.json' -Value $json -Encoding utf8NoBOM
        & jar uf $Jar meteor-client-refmap.json
        if ($LASTEXITCODE -ne 0) { throw 'Could not update the Meteor JAR refmap.' }
    } finally {
        Pop-Location
    }
} finally {
    Remove-Item -LiteralPath $temporaryDirectory -Recurse -Force
}
