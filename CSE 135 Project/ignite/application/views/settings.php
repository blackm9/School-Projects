<!-- Content
================================================== -->
<div class="row-fluid" style="padding-top: 50px;">
  <div class="container-fluid">
    <div class="span6 offset3">
    <h1>Settings</h1>
    <? if(isset($changed)) {echo '<br><h2 class="text-success">Password Changed.</h2>';} ?>
    </div>
  </div>
</div>
<div class="row-fluid">
  <div class="container-fluid">
    <div class="well span6 offset3">
      <form class="form-horizontal" method="post" action="settings" accept-charset="UTF-8">
	    <fieldset>
          <legend>Change Password</legend>
		</fieldset>
        <div class="control-group">
          <label class="control-label" for="oldPassword">Old Password</label>
          <div class="controls">
            <input type="password" name="oldPassword" placeholder="Old Password">
            <?= form_error('oldPassword') ?>
            <? if(isset($wrongpassword)) {echo '<p class="text-error">Your password is incorrect.</p>';} ?>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputPassword">New Password</label>
            <div class="controls">
              <input type="password" name="inputPassword" placeholder="New Password">
              <?= form_error('inputPassword') ?>
            </div>
          </div>
        <div class="control-group">
          <label class="control-label" for="confirmPassword">Confirm New Password</label>
          <div class="controls">
            <input type="password" name="confirmPassword" placeholder="Confirm New Password">
            <?= form_error('confirmPassword') ?>
          </div>
        </div>
        <div class="control-group">
          <div class="controls">
            <button type="submit" class="btn">Submit</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
</html>