<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
class Signup extends CI_Controller {
	
	public function __construct()
	{
		parent::__construct();
		$this->load->model('users_model');
	}

	public function index()
	{

		// if doing a quick signup from the front page, confirm fields are not needed
		$this->load->library('form_validation');
		$quickstart = isset($_POST['quickstart']);

		$this->form_validation->set_error_delimiters('<p class="text-error">', '</p>');
		$this->form_validation->set_message('is_unique', 'There is already an account for that Email.');

		$this->form_validation->set_rules('email', 'Your Email', 'required|valid_email|is_unique[users.email]');
		$this->form_validation->set_rules('password', 'Password', 'required|min_length[8]');
		if (!$quickstart) {
	        $this->form_validation->set_rules('confirm_email', 'Confirm Email', 'required|valid_email|matches[email]');
	        $this->form_validation->set_rules('confirm_password', 'Confirm Password', 'required|matches[password]');
   		}

		if ($this->form_validation->run() == FALSE)
		{

			$data['title'] = 'Quiz Maker - Sign Up';
			$data['login'] = false;
			$data['signup'] = true;

			$this->load->view('templates/header', $data);
			$this->load->view('templates/navbar_login', $data);
			$this->load->view('signup', $data);
			$this->load->view('templates/footer', $data);
		}
		else
		{
			$result = $this->users_model->add_user();
			$this->load->library('session');
			$this->session->set_userdata('userid', $result->id);
			$this->session->set_userdata('email', $result->email);
			$this->session->set_userdata('access', $result->access);
			redirect('dashboard');
		}
	}
}