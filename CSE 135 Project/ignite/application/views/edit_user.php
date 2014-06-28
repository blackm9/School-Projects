<!-- Content
================================================== -->
<h2 class="text-center">Edit <?= ucfirst($user->access) . " $user->email" ?></h2>

<div class="container">
  <form action="<?= $user->id ?>" method="POST">
    <fieldset>
      <div class="form-actions">
        <? if ($user->access != 'student') {
          echo '<button type="submit" class="btn btn-primary" name="edit" value="student">Change to Student</button>';
        } ?>
        <? if ($user->access != 'grader') {
          echo '<button type="submit" class="btn btn-primary" name="edit" value="grader">Change to Grader</button>';
        } ?>
        <? if ($user->access != 'teacher') {
          echo '<button type="submit" class="btn btn-primary" name="edit" value="teacher">Change to Teacher</button>';
        } ?>
        <button type="submit" class="btn" name="edit" value="cancel">Cancel</button>
      </div>
    </fieldset>
  </form>
</div>