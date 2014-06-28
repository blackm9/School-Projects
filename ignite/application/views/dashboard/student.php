<!-- Content
================================================== -->
<h2 class="text-center">Welcome Student!</h2>

<div class="container">
  <h4>Pending:</h4>
  <table class= "table table-striped table-bordered table-hover">
    <thead>
      <tr>
        <th>Subject</th>
        <th>Max Score</th>
        <th>Due Date</th>
      </tr>
    </thead>
    <tbody>
      <? foreach ($pending as $quiz) { ?>
      <tr>
        <td><a href="quiz/take/<?= $quiz->id ?>"><?= $quiz->name ?></a></td>
        <td><?= $quiz->points ?></td>
        <td><?= $quiz->due ?></td>
      </tr>
      <? } ?>
    </tbody>
  </table>
  <hr>
  <h4> Completed: </h4>
  <table class= "table table-striped table-bordered table-hover">
    <thead>
      <tr>
        <th>Subject</th>
        <th>Your Score</th>
        <th>Max Score</th>
        <th>Due Date</th>
        <th>Submission Status</th>
      </tr>
    </thead>
    <tbody>
      <? foreach ($completed as $quiz) {
        switch($quiz->status) {
          case 'Submitted':
            $url = "quiz/view/$quiz->id";
            break;
          case 'Graded':
            $url = "quiz/viewgrade/$quiz->id";
            break;
          case 'Graded -- Regrade Requested':
            $url = "quiz/viewregrade/$quiz->id";
            break;
          case 'Graded -- Regrade Complete':
            $url = "quiz/viewregraded/$quiz->id";
            break;
        }
      ?>
      <tr>
        <td><a href="<?= $url ?>"><?= $quiz->name ?></a></td>
        <td><?= $quiz->status == 'Submitted' ? '--' : $quiz->grade ?></td>
        <td><?= $quiz->points ?></td>
        <td><?= $quiz->due ?></td>
        <td><?= $quiz->status ?></td>
      </tr>
      <? } ?>
    </tbody>
  </table>
</div>
