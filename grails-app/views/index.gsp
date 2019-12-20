<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>
</head>
<body>
    <h1>Gerador de Conciliação</h1>
    <g:form controller="conciliacao">
        <label for="mes">Mês</label>
        <g:select name="mes" from="${1..12}"/>
        <g:actionSubmit value="Obter JSON" action="obtenhaConciliacaoEmJson"/>
        <g:actionSubmit value="Obter CSV" action="obtenhaConciliacaoEmCsv"/>
    </g:form>
</body>
</html>
