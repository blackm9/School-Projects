<!-- Content
================================================== -->
<h1 class="text-center">User Management</h1>

<div class="container">
  <form action="users" method="POST">
    <fieldset>
      <legend>Add User</legend>
      <label>Email address</label>
      <input name="email" type="text" placeholder="New User's Email"><br />
      <?= form_error('email') ?>
      <input name="password" type="text" placeholder="New User's Password"><br />
      <?= form_error('password') ?>
      <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="add" value="student">Add as Student</button>
        <button type="submit" class="btn btn-primary" name="add" value="grader">Add as Grader</button>
        <button type="submit" class="btn btn-primary" name="add" value="teacher">Add as Teacher</button>
      </div>
    </fieldset>
  </form>
</div>

<div class="container">
  <p>Quiz Maker Users:</p>
  <table class= "table table-striped table-bordered table-hover">
    <thead>
      <tr>
        <th>Email</th>
        <th>Type</th>
      </tr>
    </thead>
    <tbody>
      <? foreach ($users as $user) { ?>
      <tr>
        <td><?= $user->email ?> <? if($email != $user->email)
          {
            echo "<a href=\"users/delete/$user->id>\"><i class=\"icon-trash\"></i> Delete</a>"; 
          }
        ?></td>
        <td><?= ucfirst($user->access) ?> <? if($email != $user->email)
          {
            echo "<a href=\"users/edit/$user->id\"><i class=\"icon-pencil\"></i> Edit</a>";
          }
        ?></td>
      </tr>
      <? } ?>
    </tbody>
  </table>
</div>