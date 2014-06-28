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
<header class="jumbotron subhead" id="overview">
    <div class="container">
        <form>
            <fieldset>
                <script>
  document.writeln('<legend>' + quizData.settings.quizName + '</legend>');
  document.writeln('<div class="inline-block span3">' + 'Time Limit: ' + quizData.settings.timeLimit + ' minute(s)' + '</div>');
  document.writeln('<div class="inline-block offset1">Possible Point(s): ' + quizData.settings.possiblePoints + '</div><br>');
  document.writeln('<div class="inline-block span3">Start Date: ' + quizData.settings.startDate + '</div>');
  document.writeln('<div class="inline-block offset1">Start Time: ' + quizData.settings.startTime + '</div><br>');
  document.writeln('<div class="inline-block span3">End Date: ' + quizData.settings.endDate + '</div>');
  document.writeln('<div class="inline-block offset1">End Time: ' + quizData.settings.endTime + '</div>');
                </script>
            </fieldset>
        </form>
    </div>
</header>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
        </div>
        <div class="span8">
            <form action = "" method="post">
                <fieldset>
<script>
  // Loop and print all the questions in the quiz
  $.each($(quizData.questions), function (i, q) {
    document.writeln(previewQuestion(q));
  });		
				
</script>		
                </fieldset>
            </form>
        </div>
    </div>
</div>

<div class="container">
    <section id="contents">
        <div class="form-actions">
          <span class="pull-right">
            <input type="submit" value="Submit" class="btn btn-success" onclick="saveQuizTaken(); setTimeout(function(){window.location.replace('../../dashboard');}, 500);">
          </span>
        </div>
    </section>
</div>
  
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