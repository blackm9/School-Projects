<!-- Navbar
================================================== -->
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
        <a class="brand" href="index.html" style="float:left;">Quiz Maker</a>
        <div style="float:right;margin:0;">
          <ul class="nav">
            <li class="<?= $login ? 'active' : '' ?>"><a href="<?= site_url('login') ?>" class="nav_links">Log in</a></li>
            <li class="<?= $signup ? 'active' : '' ?>"><a href="<?= site_url('signup') ?>" class="nav_links">Sign up</a><li>
          </ul>
        </div>
    </div>
  </div>
</div>