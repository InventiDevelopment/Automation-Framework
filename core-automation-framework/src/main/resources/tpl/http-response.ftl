<html>
    <#-- @ftlvariable name="data" type="cz.inventi.qa.framework.core.allure.HttpResponseAttachment" -->
    <head>
        <meta http-equiv="content-type" content="text/html; charset = UTF-8">
        <style>
            pre {
                white-space: pre-wrap;
            }
        </style>
    </head>
    <body>
        <div><h4>Status code</h4>
            <#if data.responseCode??>
                <pre><code><b>${data.responseCode}</b></code></pre>
            <#else>Unknown</#if>
        </div>

        <#if data.url??>
            <div>
                <pre><code>${data.url}</code></pre>
            </div>
        </#if>

        <#if data.body??>
            <h4>Body</h4>
            <div>
                <pre><code>${data.body}</code></pre>
            </div>
        </#if>

        <#if (data.headers)?has_content>
            <h4>Headers</h4>
            <div>
                <#list data.headers as nameAndValue>
                    <pre><code><b>${nameAndValue}</code></pre>
                </#list>
            </div>
        </#if>
    </body>
</html>