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
    <div class="span6 offset3">
  <h3>Let's get started!</h3>
    <p>We will send you an e-mail containing instructions for how you can recover your password.</p>
        <form method="POST" action="recovery" accept-charset="UTF-8">
            <input class="span3" placeholder="E-mail" type="text" name="email"><br />
            <?= form_error('email') ?>
            <? if(isset($notfound)) {echo '<p class="text-error">Your Email address is incorrect or not found.</p>';} ?>
            <button class="btn-info btn" type="submit">Send e-mail</button>      
        </form>    
    </div>
  </div>
</div>

