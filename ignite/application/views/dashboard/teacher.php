<!-- Subhead
    ================================================== -->
  <header class="jumbotron subhead" id="overview">
    <div class="container"> 

      <!-- Quiz Settings -->
      <form id="quizForm" action="" method="post" enctype="multipart/form-data">
        <fieldset id="quizSettings">
          <legend><i class="icon-wrench"></i> Quiz Settings <span class="pull-right">
            <button class="btn btn-warning" title="Settings Help" onclick="tutorialsettings(); return false;"><i class="icon-question-sign"></i> Quiz Settings Help</button>
          </span></legend>
          <div class="inline-block well">
            <label for="loadQuiz">Load Saved Quiz</label>


            <div class="input-append">
              <select name="loadQuizSelect" id="loadQuizSelect" class="span2">
                <option value="">--</option>
                <? foreach ($quizzes as $quiz) { ?>
                <option value="<?= $quiz->id ?>"><?= $quiz->name ?></option>
                <? } ?>
              </select>
              <button class="btn btn-primary" id="loadQuiz" type="button" >Load</button>
            </div>
          </div>
          <div class="inline-block well">
            <label for="quizName">Quiz Name</label>
            <input type="text" name="quizName" id="quizName" placeholder="Quiz Name" class="span2">
          </div>
          <div class="inline-block well">
            <label for="timeLimit">Time Limit</label>
            <div class="input-append">
              <input name="timeLimit" class="input-small" id="timeLimit" type="number" min="1" step="1" placeholder="None">
              <span class="add-on">minutes</span> </div>
            </div>
            <div class="inline-block well">
              <label for="possiblePoints">Possible Points</label>
              <input type="number" name="possiblePoints" class="input-small" min="1" id="possiblePoints" value="1">
            </div>
            <div class="inline-block well">
              <label for="viewAnswers">Reveal Answers</label>
              <select id="viewAnswers" name="viewAnswers" class="span2">
                <option value="1" selected>Never</option>
                <option value="2">After Deadline</option>
                <option value="3">On Turn-in</option>
              </select>
            </div>
            <br />
            <div class="inline-block well">
              <label for="startDate">Quiz Opens</label>
              <div class="input-append">
                <input type="date" name="startDate" id="startDate" class="span2">
                <input type="time" name="startTime" id="startTime" class="span2">
              </div>
              <span class="help-block"><small>The date & time the quiz opens to students</small></span> </div>
              <div class="inline-block well">
                <label for="endDate">Deadline</label>
                <div class="input-append">
                  <input type="date" name="endDate" id="endDate" class="span2">
                  <input type="time" name="endTime" id="endTime" class="span2">
                </div>
                <span class="help-block"><small>The date & time the quiz closes to students</small></span> </div>
                <div class="inline-block well">
                  <label for="randomizeTaker">Display Questions in</label>
                  <select id="randomizeTaker" name="randomizeTaker" class="span2">
                    <option value="1" selected>Fixed Order</option>
                    <option value="2">Randomized Order</option>
                  </select>
                  <span class="help-block">How to display questions to the students.</span> </div>
                </fieldset>
              </form>
            </div>
          </header>

<!-- Sidebar and Content
  ================================================== -->
  <div class="container">
    <div class="row"> 

    <!-- Sidebar
    ================================================== -->
    <div class="span3 bs-docs-sidebar" id="amigoSidebar">
      <ol class="nav nav-list bs-docs-sidenav affix-top sortable">
        <li class="not-sortable"><a href="#"><i class="icon-chevron-up"></i> Back To Top</a></li>
        <li class="not-sortable">Points Allocated: <span id="totalPoints">0</span> / <span id="pointsPossible">1</span> </li>
        <li class="not-sortable" id="tourSortID"><a href="#" id="sorter"><i class="icon-random"></i> Reorder by Question Type</a></li>
        <li class="divider not-sortable" style="visibility:hidden"></li>
      </ol>
    </div>
    
    <!-- Content
    ================================================== -->
    <div class="span9">
      <section id="contents"> 

        <!-- Add question -->
        <fieldset>
          <legend><i class="icon-plus"></i> Add Question <span class="pull-right">
            <button class="btn btn-warning" title="Question Creation Help" onclick="tutorialBase();"><i class="icon-question-sign"></i> Question Creation Help</button>
          </span> </legend>
          <input type="number" id="numToAdd" class="input-small" min="1" max="100" value="1" placeholder="# to add" />
          <select name="addQuestion" id="addQuestions">
            <option value="">Select Type</option>
            <option value="mc">Multiple Choice</option>
            <option value="tf">True / False</option>
            <option value="m">Matching</option>
            <option value="fi">Fill-in</option>
            <option value="sa">Short Answer</option>
          </select>
        </fieldset>
        
        <!-- Save Preview Clear -->
        <div class="form-actions">
          <input type="submit" name="save" value="Save Quiz" class="btn btn-success" id="save-bottom" onclick="$('#quizForm').submit();"/>
          <input type="button" value="Preview Quiz" class="btn" id="preview-bottom" onclick="preview()"/>
          <input type="reset" value="Start Over" class="btn btn-danger" id="clearAll-bottom" onclick="clearPage();"/>
        </div>
        
        <!-- New question inserted here -->
        <div id="questions"></div>
      </section>
    </div>
  </div>
</div>

<!-- Tour
  ================================================== -->
  <section id="toolTipTour"> 

    <!-- Basic Tour -->
    <ol id="tour" style="display:none">
      <li data-id="numToAdd" data-options="tipLocation:top;tipAnimation:fade" data-button="10 Million!">
        <p>1. How many questions do you want to add? (1 is default)</p>
      </li>
      <li data-id="addQuestions" data-options="tipLocation:top" data-button="I did.">
        <p>2. Select the type of question you'd like to add.</p>
      </li>
      <li data-id="addQuestions" data-options="tipLocation:bottom" data-button="Okay.">
        <p>3. Now try another type of question. Add more than one if you haven't already!</p>
      </li>
      <li data-id="tourSortID" data-options="tipLocation:right;scroll:false;" data-button="Awesome!">
        <p>4. Now that you have multiple question types, you can group them into sections by hitting this button.</p>
      </li>
      <li data-id="amigoSidebar" data-options="tipLocation:bottom" data-button="Whoa!">
        <p>5. You can also drag the questions around in the sidebar to change their order.</p>
      </li>
      <li data-id="save-bottom" data-options="tipLocation:left" data-button="I'll remember.">
        <p>6. Dont forget to save your quiz before you leave! You can come back to it later.</p>
      </li>
      <li data-button="Let's do this!">
        <p>This concludes the first tutorial. You can select the help button on any created question for more tips about that type.</p>
      </li>
    </ol>

    <!-- Settings Tour -->
    <ol id="toursettings" style="display:none">
      <li data-id="loadQuizSelect" data-options="tipLocation:bottom;tipAnimation:fade" data-button="Next">
        <p>Select a previously saved quiz here.</p>
      </li>
      <li data-id="quizName" data-options="tipLocation:bottom" data-button="Next">
        <p>Name this quiz.</p>
      </li>
      <li data-id="timeLimit" data-options="tipLocation:bottom" data-button="Next">
        <p>Leave blank for unlimited time.</p>
      </li>
      <li data-id="possiblePoints" data-options="tipLocation:bottom" data-button="Next">
        <p>The total points that this quiz is worth relative to students' final grade.</p>
      </li>
      <li data-id="viewAnswers" data-options="tipLocation:bottom" data-button="Next">
        <p>When can the students see the answer key?</p>
      </li>
      <li data-id="startDate" data-options="tipLocation:bottom" data-button="Next">
        <p>When will the quiz be available?</p>
      </li>
      <li data-id="endDate" data-options="tipLocation:bottom" data-button="Next">
        <p>When is the quiz due?</p>
      </li>
      <li data-id="randomizeTaker" data-options="tipLocation:bottom" data-button="Next">
        <p>In what order should the questions be presented to students?</p>
      </li>
      <li data-button="I'm ready!">
        <p>This completes the quiz settings tutorial. Next, start adding questions. For more help, click the orange Question Creation Help button in the Add Question section of this page.</p>
      </li>
    </ol>

    <!-- True/False -->
    <ol id="tf" style="display:none">
      <li data-button="Great!">
        <p>True/False:</p>
        <ol>
          <li>(Optional) Name the question. Not visible to taker.</li>
          <li>Type your question.</li>
          <li>(Optional) Select Graphic.</li>
          <li>Select correct answer. (True or False)</li>
          <li>Choose how many points the question is worth.</li>
        </ol>
      </li>
    </ol>

    <!-- Multiple Choice -->
    <ol id="mc" style="display:none">
      <li data-options="tipLocation:top;tipAnimation:fade" data-button="Great!">
        <p>Multiple Choice:</p>
        <ol>
          <li>(Optional) Name the question. Not visible to taker.</li>
          <li>Type your question.</li>
          <li>(Optional) Select Graphic.</li>
          <li>Choose either one or more than one correct answers.</li>
          <li>Fill in answers and select the correct one.</li>
          <li>(Optional) Add more answers.</li>
          <li>Choose how many points the question is worth.</li>
        </ol>
      </li>
    </ol>

    <!-- Short Answer -->
    <ol id="sa" style="display:none">
      <li data-options="tipLocation:top;tipAnimation:fade" data-button="Great!">
        <p>Short Answer:</p>
        <ol>
          <li>(Optional) Name the question. Not visible to taker.</li>
          <li>Type your question.</li>
          <li>(Optional) Select Graphic.</li>
          <li>Choose how many points the question is worth.</li>
          <li>This type of question must be manually graded.</li>
        </ol>
      </li>
    </ol>

    <!-- Fill In -->
    <ol id="fi" style="display:none">
      <li data-options="tipLocation:top;tipAnimation:fade" data-button="Great!">
        <p>Fill-In:</p>
        <ol>
          <li>(Optional) Name the question. Not visible to taker.</li>
          <li>Type your question, using underscores ("_____") to indicate a blank.</li>
          <li>(Optional) Select Graphic.</li>
          <li>Fill in possible correct answers.</li>
          <li>(Optional) Add more answers.</li>
          <li>Choose how many points the question is worth.</li>
        </ol>
      </li>
    </ol>

    <!-- Matching -->
    <ol id="m" style="display:none">
      <li data-options="tipLocation:top;tipAnimation:fade" data-button="Great!">
        <p>Matching:</p>
        <ol>
          <li>(Optional) Name the question. Not visible to taker.</li>
          <li>(Optional) Select Graphic.</li>
          <li>Type primary word or phrase on left, with the matching value on the right. The right column will be randomized for the test taker.</li>
          <li>(Optional) Add more answers.</li>
          <li>Choose how many points the question is worth.</li>
        </ol>
      </li>
    </ol>
  </section>