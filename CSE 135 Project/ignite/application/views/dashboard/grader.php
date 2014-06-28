<!-- Content
================================================== -->
<h2 class="text-center">Welcome Grader!</h2>

<div class="container">
  <h4>Pending:</h4>
  <table class= "table table-striped table-bordered table-hover">
    <thead>
      <tr>
        <th>Quiz</th>
        <th>Student</th>
        <th>Status</th>
        <th>Due Date</th>
      </tr>
    </thead>
    <tbody>
      <? foreach ($quizzes as $quiz) { 
        switch($quiz->status) {
          case 'Submitted':
            $url = "quiz/grade/$quiz->id";
            break;
          case 'Graded -- Regrade Requested':
            $url = "quiz/regrade/$quiz->id";
            break;
          }
      ?>
      <tr>
        <td><a href="<?= $url ?>"><?= $quiz->name ?></a></td>
        <td><?= $quiz->email ?></td>
        <td><?= $quiz->status ?></td>
        <td><?= $quiz->due ?></td>
      </tr>
      <? } ?>
    </tbody>
  </table>
</div>
