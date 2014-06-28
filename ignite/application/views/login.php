<!-- Content
================================================== -->
<div class="row-fluid" style="padding-top: 50px;">
    <div class="container-fluid">
      <div class="well span3 offset3">
        <fieldset>
        <legend>Log in to Quiz Maker</legend>
        </fieldset>
        <form method="POST" action="login" accept-charset="UTF-8">
            <input placeholder="Email Address" type="text" name="email" value="<?= set_value('email') ?>"><br />
            <?= form_error('email') ?>
            <? if(isset($notfound)) {echo '<p class="text-error">Your Email address is incorrect or not found.</p>';} ?>
            <input placeholder="Password" type="password" name="password"> 
            <?= form_error('password') ?>
            <? if(isset($wrongpassword)) {echo '<p class="text-error">Your password is incorrect.</p>';} ?>
            <button class="btn-primary btn" type="submit">Login</button>      
        </form>    
      </div>
	<div class="span3" style="padding-top: 50px;">
	  <p>Don't have an account?<br />
	  <a href="signup">Sign up now!</a></p>
	  <p>Did you forget your password?<br />
	  <a href="recovery">Recover it here.</a></p>
	</div>
    </div>
</div>
