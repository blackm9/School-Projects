<!-- Content
================================================== -->
<div class="row-fluid" style="padding-top: 50px;">
  <div class="container-fluid">
    <div class="span6 offset3">
  	  <h1>Recover your password</h1>
    </div>
  </div>
</div>

<div class="row-fluid">
  <div class="container-fluid">
    <div class="well span6 offset3">
	  <fieldset>
        <legend>Now, choose a new password</legend>
      </fieldset>
      <form class="form-horizontal" method="POST" action="<?= current_url() ?>" accept-charset="UTF-8">

	  <div class="control-group">
		<label class="control-label"  for="password">Password</label>
		<div class="controls">
		  <input type="password" id="password" name="password" placeholder="" class="input-xlarge">
		  <?= form_error('password') ?>
		</div>
	  </div>
	
	  <div class="control-group">
		<label class="control-label"  for="confirm_password">Confirm Password</label>
		<div class="controls">
		  <input type="password" id="confirm_password" name="confirm_password" placeholder="" class="input-xlarge">
		  <?= form_error('confirmPassword') ?>
		</div>
	  </div>
		  
	  <div class="control-group">
		<div class="controls">
		  <button class="btn btn-success">Set New Password</button>
		</div>
	  </div>  
		  
        </form>    
      </div>
    </div>
</div>