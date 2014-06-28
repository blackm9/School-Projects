<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
class Login extends CI_Controller {
	
	public function __construct()
	{
		parent::__construct();
		$this->load->model('users_model');
	}

	public function index()
	{
		// check for existing login
		$this->load->library('session');
		if ($this->session->userdata('userid')) {
			// logged in, bounce to dashboard
			redirect('dashboard');
			return;
		}

		$this->load->library('form_validation');

		$this->form_validation->set_error_delimiters('<p class="text-error">', '</p>');

		$this->form_validation->set_rules('email', 'Your Email', 'required|valid_email');
		$this->form_validation->set_rules('password', 'Password', 'required|min_length[8]');

		$data['title'] = 'Quiz Maker - Log In';
		$data['login'] = true;
		$data['signup'] = false;

		if ($this->form_validation->run() == FALSE)
		{
			$this->_loadview($data);
		}
		else
		{
			// find user info for this email
			$query = $this->users_model->get_user();
			if ($query->num_rows() == 0) {
				// there is not a login associated with this email
				$data['notfound'] = true;
				$this->_loadview($data);
				return;
			}

			// verify password matches			
			$row = $query->row();
			if (crypt($this->input->post('password'), $row->password) == $row->password)
			{
				// set session data and bounce to dashboard
				$this->session->set_userdata('userid', $row->id);
				$this->session->set_userdata('email', $row->email);
				$this->session->set_userdata('access', $row->access);
				redirect('dashboard');
			}

			$data['wrongpassword'] = true;
			$this->_loadview($data);
		}
	}

	private function _loadview($data)
	{
		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar_login', $data);
		$this->load->view('login', $data);
		$this->load->view('templates/footer', $data);
	}
}