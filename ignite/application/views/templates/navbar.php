<!-- Navbar
  ================================================== -->
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="dashboard" style="float:left;">Quiz Maker</a>
            <div class="nav-collapse collapse">
              <div class="pull-right">
                <ul class="nav pull-right">
                    <li><a><?= $email ?></a></li>
                    <? if ($access == 'teacher')
                    {
                        echo '<li><a href="' . site_url('users') . '"><i class="icon-user"></i> Manage Users</a></li>';
                    } ?> 
                    <li><a href="<?= site_url('settings') ?>"><i class="icon-cog"></i> Settings</a></li>
                    <li><a href="<?= site_url('logout') ?>"><i class="icon-off"></i> Logout</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  