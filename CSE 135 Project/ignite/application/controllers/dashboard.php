<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
class Dashboard extends CI_Controller {
	
	public function __construct()
	{
		parent::__construct();
		$this->load->model('quizzes_model');
	}

	public function index()
	{
		// make sure we are logged in
		if (!$this->_checklogin())
			return;

		$data['title'] = 'Quiz Maker - Dashboard';
		$data['email'] = $this->session->userdata('email');
		$data['access'] = $this->session->userdata('access');
		$data['userid'] = $this->session->userdata('userid');

		switch ($data['access']) {
			case 'student':
				$this->_index_student($data);
				break;
			case 'grader':
				$this->_index_grader($data);
				break;
			case 'teacher':
				$this->_index_teacher($data);
		}
	}

	private function _checklogin() {
		$this->load->library('session');

		$access = $this->session->userdata('access');
		if ( !$access ) {
			// not logged in, bounce to login
			redirect('login');
			return false;
		}
		return true;
	}

	private function _index_student($data)
	{
		$data['pending'] = $this->quizzes_model->get_pending($data['userid']);
		$data['completed'] = $this->quizzes_model->get_completed($data['userid']);

		$this->_loadview($data);
	}

	private function _index_grader($data)
	{
		$data['quizzes'] = $this->quizzes_model->get_submissions();

		$this->_loadview($data);
	}

	private function _index_teacher($data)
	{
		$data['quizzes'] = $this->quizzes_model->get_quizzes();

		$this->_loadview($data);
	}

	private function _loadview($data)
	{
		$this->load->view($data['access'] == 'teacher' ? 'templates/header_quizmaker' : 'templates/header', $data);
		$this->load->view('templates/navbar', $data);
		$this->load->view("dashboard/$data[access]", $data);
		$this->load->view('templates/footer', $data);
	}
}