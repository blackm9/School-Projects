<link href="<?= site_url('css/bootstrap-wysihtml5.css') ?>" rel="stylesheet" type="text/css">
<link href="<?= site_url('css/wysiwyg-color.css') ?>" rel="stylesheet" type="text/css">

<!-- Le javascript
================================================== --> 
<script src="<?= site_url('scripts/jquery-1.10.2.min.js') ?>"></script> 
<script src="<?= site_url('scripts/bootstrap-wysiwyg5.min.js')?>"> </script>
<script src="<?= site_url('scripts/wysihtml5-0.3.0.min.js')?>"> ></script>
<script src="<?= site_url('assets/js/jasny-bootstrap.min.js')?>"> </script> 
<script src="<?= site_url('assets/js/bootstrap.min.js')?>"> </script>
<script src="<?= site_url('assets/js/application.js')?>"> </script> 
<script src="<?= site_url('assets/js/jquery-ui-1.10.3.custom.min.js')?>"> </script> 
<script src="<?= site_url('assets/js/jquery.cookie.min.js')?>"> </script> 
<script src="<?= site_url('assets/js/modernizr.mq.min.js')?>"> </script> 
<script src="<?= site_url('assets/js/jquery.joyride-2.1.min.js')?>"> </script> 
<script src="<?= site_url('assets/js/jquery.mockjax.min.js')?>"> </script> 
<script src="<?= site_url('assets/js/date.format.min.js')?>"> </script>

<script>
  var quizData = 
  <?= $quiz->data ?>
</script>

<!-- Content
================================================== -->
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2"></div>
        <div class="span8">
        <h4>Regrade Request:</h4>
            <div class="well">
                <p>
                    <script>
                        document.writeln(quizData.regradeRequest);
                    </script>
                </p>
            </div>
            <hr>

            <form>
                <fieldset>
<script>
  // Loop and print all the questions in the quiz
  $.each($(quizData.questions), function (i, q) {
    document.writeln(answeredQuestion(i, q));
  });     
  pointFiller();
</script>   
                </fieldset>
            </form>
  <div class="container">
  <div style="margin-top:40px">
  <h2>Regrade Request Comments</h2>
    <textarea id="richtext" class="textarea" placeholder="Enter text ..." style="width: 810px; height: 200px"></textarea>
    <script>
    $('.textarea').wysihtml5();
    </script>
  </div>
  </div>
        <div class="form-actions"><span class="pull-right">
          <button class="btn btn-warning" onclick="regradeQuizGraded(); setTimeout(function(){window.location.replace('../../dashboard');}, 500);">Update Grade</button>
          </span></div>
        </div>
    </div>
</div>

<script type="text/javascript">
  $(prettyPrint);
</script>

<!-- Footer
================================================== -->
<footer class="footer" style="padding:0">
  <div class="container">
    <p><a href="http://classes.pint.com/cse135/">CSE135 Homepage</a></p>
    <p>&copy; 2013 <a href="group8.html">Group 8</a>. All rights reserved.</p>
    <p class="pull-right"><a href="#">Back to top</a></p>
  </div>
</footer>
</body>
</html>
