{{- define "mychart.name" -}}
{{- .Chart.Name -}}
{{- end -}}

{{- define "mychart.fullname" -}}
{{- printf "%s-%s" .Release.Name .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}