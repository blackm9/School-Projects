<!-- Content
================================================== -->
<div class="row-fluid" style="padding-top: 50px;">
  <div class="container-fluid">
    <div class="well span6 offset3">
      <form class="form-horizontal" method="POST" action="signup" accept-charset="UTF-8">
        <fieldset>
          <legend>Create a New Account</legend>
		    </fieldset>
        <div class="control-group">
          <label class="control-label" for="email">Your Email</label>
          <div class="controls">
            <input type="text" id="email" name="email" placeholder="" class="input-xlarge" value="<?= set_value('email') ?>">
          </div>
          <?= form_error('email') ?>
        </div>
        <div class="control-group">
          <label class="control-label" for="confirm_email">Confirm Email</label>
          <div class="controls">
            <input type="text" id="confirm_email" name="confirm_email" placeholder="" class="input-xlarge">
          </div>
          <?= form_error('confirm_email') ?>
        </div>
      	<div class="control-group">
          <label class="control-label" for="password">Password</label>
          <div class="controls">
            <input type="password" id="password" name="password" placeholder="" class="input-xlarge">
          </div>
          <?= form_error('password') ?>
        </div>
      	<div class="control-group">
          <label class="control-label" for="confirm_password">Confirm Password</label>
          <div class="controls">
            <input type="password" id="confirm_password" name="confirm_password" placeholder="" class="input-xlarge">
          </div>
          <?= form_error('confirm_password') ?>
        </div>
        <div class="control-group">
          <div class="controls">
            <button class="btn btn-primary">Sign Up</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
