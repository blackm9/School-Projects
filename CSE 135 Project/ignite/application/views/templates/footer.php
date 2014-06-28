<!-- Footer
================================================== -->
<footer class="footer" style="padding:0">
  <div class="container">
    <p><a href="http://classes.pint.com/cse135/">CSE135 Homepage</a></p>
    <p>&copy; 2013 <a href="group8.html">Group 8</a>. All rights reserved.</p>
    <p class="pull-right"><a href="#">Back to top</a></p>
  </div>
</footer>

<!-- Le javascript
  ================================================== --> 
  <!-- Placed at the end of the document so the pages load faster --> 
<script src="scripts/jquery-1.10.2.min.js"></script> 
<script src="scripts/bootstrap-wysiwyg5.min.js"></script>
<script src="scripts/wysihtml5-0.3.0.min.js"></script>
<script src="assets/js/jasny-bootstrap.min.js"></script> 
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/application.js"></script> 
<script src="assets/js/jquery-ui-1.10.3.custom.min.js"></script> 
<script src="assets/js/jquery.cookie.min.js"></script> 
<script src="assets/js/modernizr.mq.min.js"></script> 
<script src="assets/js/jquery.joyride-2.1.min.js"></script> 
<script src="assets/js/jquery.mockjax.min.js"></script> 
<script src="assets/js/date.format.min.js"></script>



<? if ($title == 'Quiz Maker - Dashboard' && $access == 'teacher') { ?>
<script type="text/javascript">
    document.getElementById('startDate').value = (new Date()).format("yyyy-mm-dd");
    document.getElementById('startTime').value = (new Date()).format("hh:MM:ss");
    document.getElementById('endDate').value = (new Date()).format("yyyy-mm-dd");
    document.getElementById('endTime').value = (new Date()).format("hh:MM:ss");
</script>
<? } ?>
</body>
</html>
