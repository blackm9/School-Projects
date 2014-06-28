<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
class Quiz extends CI_Controller {
	
	public function __construct()
	{
		parent::__construct();
		$this->load->model('quizzes_model');
	}

	// quizmaker save new quiz function
	public function save()
	{
		// check for teacher login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'teacher') {
			// logged in, bounce to dashboard
			redirect('dashboard');
			return;
		}

		$this->load->library('form_validation');
		$this->form_validation->set_rules('id', 'id', 'required|exact_length[36]|xss_clean');
		$this->form_validation->set_rules('name', 'name', 'required|xss_clean');
		$this->form_validation->set_rules('points', 'points', 'required|numeric|xss_clean');
		$this->form_validation->set_rules('open', 'open', 'required|xss_clean');
		$this->form_validation->set_rules('due', 'due', 'required|xss_clean');
		$this->form_validation->set_rules('data', 'data', 'required|xss_clean');

		if ($this->form_validation->run())
		{
			// valid & sanitized data, insert or update in database
			$this->quizzes_model->replace_quiz();
		}
		else
		{
			redirect('dashboard');
		}
	}

	// save a submitted quiz that a student has completed
	public function savetaken()
	{
		// check for student login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'student') {
			// logged in, bounce to dashboard
			redirect('dashboard');
			return;
		}

		$this->load->library('form_validation');
		$this->form_validation->set_rules('id', 'id', 'required|exact_length[36]|xss_clean');
		$this->form_validation->set_rules('data', 'data', 'required|xss_clean');

		if ($this->form_validation->run())
		{
			// valid & sanitized data, insert or update in database
			$this->quizzes_model->insert_submission($this->session->userdata('userid'));
		}
		else
		{
			redirect('dashboard');
		}
	}

	// save a graded quiz that a grader has graded
	public function savegraded()
	{
		// check for grader login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'grader') {
			// logged in, bounce to dashboard
			redirect('dashboard');
			return;
		}

		$this->load->library('form_validation');
		$this->form_validation->set_rules('data', 'data', 'required|xss_clean');
		$this->form_validation->set_rules('grade', 'points', 'required|numeric');

		if ($this->form_validation->run())
		{
			// valid & sanitized data, update in database
			$this->quizzes_model->update_submission($this->session->userdata('submissionid'), 'Submitted', 'Graded');
			$this->session->unset_userdata('submissionid');
		}
		else
		{
			redirect('dashboard');
		}
	}

	// save a regraded quiz that a grader has regraded
	public function saveregraded()
	{
		// check for grader login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'grader') {
			// logged in, bounce to dashboard
			redirect('dashboard');
			return;
		}

		$this->load->library('form_validation');
		$this->form_validation->set_rules('data', 'data', 'required|xss_clean');
		$this->form_validation->set_rules('grade', 'points', 'required|numeric');

		if ($this->form_validation->run())
		{
			// valid & sanitized data, update in database
			$this->quizzes_model->update_submission($this->session->userdata('submissionid'), 'Graded -- Regrade Requested', 'Graded -- Regrade Complete');
			$this->session->unset_userdata('submissionid');
		}
		else
		{
			redirect('dashboard');
		}
	}


	// load a quiz for quizmaker
	public function load($id)
	{
		// check for teacher login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'teacher') {
			// logged in, bounce to dashboard
			redirect('dashboard');
			return;
		}

		if (strlen($id) != 36)
		{
			// not the right length for a quiz id
			redirect('dashboard');
			return;
		}

		$query = $this->quizzes_model->get_quiz($id);
		if ($query->num_rows() == 0)
		{
			// quiz id not found
			redirect('dashboard');
			return;
		}

		echo $query->row()->data;
	}

	// view a submitted/ungraded quiz for students
	public function view($id)
	{
		// check for student login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'student') {
			redirect('dashboard');
			return;
		}

		$query = $this->quizzes_model->get_submission($id, 'Submitted');
		if ($query->num_rows() == 0) {
	    	// there is not an ungraded quiz associated with this id
			redirect('dashboard');
	      	return;
	    }

		$data['title'] = 'Quiz Maker - View Quiz';
		$data['email'] = $this->session->userdata('email');
		$data['access'] = $this->session->userdata('access');
		$data['userid'] = $this->session->userdata('userid');
		$data['quiz'] = $query->row();

		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar', $data);
		$this->load->view('quiz/view', $data);
	}

	// take an untaken quiz for students
	public function take($id)
	{
		// check for student login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'student') {
			// logged in, bounce to dashboard
			redirect('dashboard');
			return;
		}

		if (strlen($id) != 36)
		{
			// not the right length for a quiz id
			redirect('dashboard');
			return;
		}

		// make sure the student hasn't taken this quiz
		if ($this->quizzes_model->has_taken($this->session->userdata('userid'), $id))
		{
			redirect('dashboard');
			return;
		}

		$query = $this->quizzes_model->get_quiz($id);
		if ($query->num_rows() == 0) {
	    	// there is not a quiz associated with this id
			redirect('dashboard');
	      	return;
	    }

		$data['title'] = 'Quiz Maker - Take Quiz';
		$data['email'] = $this->session->userdata('email');
		$data['access'] = $this->session->userdata('access');
		$data['userid'] = $this->session->userdata('userid');
		$data['quiz'] = $query->row();

		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar', $data);
		$this->load->view('quiz/take', $data);
	}

	// grade an ungraded quiz for graders
	public function grade($id)
	{
		// check for grader login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'grader') {
			redirect('dashboard');
			return;
		}

		$query = $this->quizzes_model->get_submission($id, 'Submitted');
		if ($query->num_rows() == 0) {
	    	// there is not a quiz associated with this id
	    	// or the quiz associated with this id is not in the right state ("Submitted")
			redirect('dashboard');
	      	return;
	    }

		$data['title'] = 'Quiz Maker - Grade Quiz';
		$data['email'] = $this->session->userdata('email');
		$data['access'] = $this->session->userdata('access');
		$data['userid'] = $this->session->userdata('userid');
		$data['quiz'] = $query->row();

		// store which quiz we are grading
		$this->session->set_userdata('submissionid', $id);

		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar', $data);
		$this->load->view('quiz/grade', $data);
	}

	// view a graded quiz for students
	public function viewgrade($id)
	{
		// check for student login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'student') {
			redirect('dashboard');
			return;
		}

		$query = $this->quizzes_model->get_submission($id, 'Graded');
		if ($query->num_rows() == 0) {
	    	// there is not a graded quiz associated with this id
			redirect('dashboard');
	      	return;
	    }

		$data['title'] = 'Quiz Maker - View Grade';
		$data['email'] = $this->session->userdata('email');
		$data['access'] = $this->session->userdata('access');
		$data['userid'] = $this->session->userdata('userid');
		$data['quiz'] = $query->row();

		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar', $data);
		$this->load->view('quiz/viewgrade', $data);
	}

	// view a regraded quiz for students
	public function viewregraded($id)
	{
		// check for student login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'student') {
			redirect('dashboard');
			return;
		}

		$query = $this->quizzes_model->get_submission($id, 'Graded -- Regrade Complete');
		if ($query->num_rows() == 0) {
	    	// there is not a regraded quiz associated with this id
			redirect('dashboard');
	      	return;
	    }

		$data['title'] = 'Quiz Maker - View Regrade';
		$data['email'] = $this->session->userdata('email');
		$data['access'] = $this->session->userdata('access');
		$data['userid'] = $this->session->userdata('userid');
		$data['quiz'] = $query->row();

		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar', $data);
		$this->load->view('quiz/viewregraded', $data);
	}

	// request a regrade on a graded quiz for students
	public function requestregrade()
	{
		// check for student login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'student') {
			// logged in, bounce to dashboard
			redirect('dashboard');
			return;
		}

		$this->load->library('form_validation');
		$this->form_validation->set_rules('id', 'id', 'required|exact_length[36]|xss_clean');
		$this->form_validation->set_rules('data', 'data', 'required|xss_clean');

		if ($this->form_validation->run())
		{
			// valid & sanitized data, insert or update in database
			$this->quizzes_model->update_submission_by_id_pair($this->session->userdata('userid'), $this->input->post('id'), 'Graded', 'Graded -- Regrade Requested');
		}
		else
		{
			redirect('dashboard');
		}
	}

	// view a regrade requested quiz for students
	public function viewregrade($id)
	{
		// check for student login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'student') {
			redirect('dashboard');
			return;
		}

		$query = $this->quizzes_model->get_submission($id, 'Graded -- Regrade Requested');
		if ($query->num_rows() == 0) {
	    	// there is not a graded quiz associated with this id
			redirect('dashboard');
	      	return;
	    }

		$data['title'] = 'Quiz Maker - View Grade';
		$data['email'] = $this->session->userdata('email');
		$data['access'] = $this->session->userdata('access');
		$data['userid'] = $this->session->userdata('userid');
		$data['quiz'] = $query->row();

		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar', $data);
		$this->load->view('quiz/viewregrade', $data);
	}

	// regrade a regrade requested quiz for graders
	public function regrade($id)
	{
		// check for grader login
		$this->load->library('session');
		if ($this->session->userdata('access') != 'grader') {
			redirect('dashboard');
			return;
		}

		$query = $this->quizzes_model->get_submission($id, 'Graded -- Regrade Requested');
		if ($query->num_rows() == 0) {
	    	// there is not a quiz associated with this id
	    	// or the quiz associated with this id is not in the right state ("Submitted")
			redirect('dashboard');
	      	return;
	    }

		$data['title'] = 'Quiz Maker - Regrade Quiz';
		$data['email'] = $this->session->userdata('email');
		$data['access'] = $this->session->userdata('access');
		$data['userid'] = $this->session->userdata('userid');
		$data['quiz'] = $query->row();

		// store which quiz we are grading
		$this->session->set_userdata('submissionid', $id);

		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar', $data);
		$this->load->view('quiz/regrade', $data);
	}
}