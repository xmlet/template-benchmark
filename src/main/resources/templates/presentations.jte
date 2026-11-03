@import java.util.List
@import com.mitchellbosecke.benchmark.model.Presentation
@import gg.jte.support.ForSupport

@param Iterable<Presentation> presentationItems

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="content-language" content="IE=Edge">
    <title>
        JFall 2013 Presentations - htmlApi
    </title>
    <link rel="Stylesheet" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" media="screen">
</head>
<body>
<div class="container">
    <div class="page-header">
        <h1>
            JFall 2013 Presentations - htmlApi
        </h1>
    </div>
    @for(ForSupport<Presentation> itemLoop : ForSupport.of(presentationItems))
        !{var item = itemLoop.get();}
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">
                    ${item.getTitle() + " - " + item.getSpeakerName()}
                </h3>
            </div>
            <div class="panel-body">
                ${item.getSummary()}
            </div>
        </div>
    @endfor
</div>
<script src="/webjars/jquery/3.1.1/jquery.min.js">
</script>
<script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js">
</script>
</body>
</html>
