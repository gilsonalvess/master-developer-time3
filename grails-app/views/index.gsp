<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>
</head>
<body>
    <g:form controller="conciliacao">
        <label for="mes">Mês</label>
        <input type="text" name="mes" id="mes">
        <g:actionSubmit value="Obter JSON" action="obtenhaConciliacaoEmJson"/>
        <g:actionSubmit value="Obter CSV" action="obtenhaConciliacaoEmCsv"/>
    </g:form>
</body>
</html>
