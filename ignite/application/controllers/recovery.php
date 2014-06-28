<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
class Recovery extends CI_Controller {
	
	public function __construct()
	{
		parent::__construct();
		$this->load->model('users_model');
		$this->load->helper('url');
	}

	public function index()
	{
		// make sure we are not logged in 
		if (!$this->_checklogin())
		{
			return;
		}

		$this->load->library('form_validation');

		$this->form_validation->set_error_delimiters('<p class="text-error">', '</p>');
		$this->form_validation->set_rules('email', 'email', 'required|valid_email');
		
		$data['title'] = 'Quiz Maker - Recovery';
		$data['login'] = false;
		$data['signup'] = false;


		if ($this->form_validation->run() == FALSE)
		{
			$this->_loadview($data);
		}
		else
		{
			// find user info for this email
			$query = $this->users_model->get_user();
			if ($query->num_rows() == 0) 
			{
				// there is not a login associated with this email
				$data['notfound'] = true;
				$this->_loadview($data);
				return;
			}
			else
			{
				//set token and redirect to recoveryconfirm
				$generated_token = $this->users_model->set_reset_token($this->input->post('email'));
				$this->load->helper('email');
				send_email($this->input->post('email'), 'QuizMaker Password Reset', site_url("recovery/new_password/$generated_token"));
				redirect('recoveryconfirm.html');
			}
		}
	}

	public function new_password($token = "")
	{
		// make sure we are not logged in 
		if (!$this->_checklogin())
		{
			return;
		}

		$data['title'] = 'Quiz Maker - Recovery';
		$data['login'] = false;
		$data['signup'] = false;

		
		if(strlen($token) != 32)
		{
			redirect('recovery');
			return;
		}



		//check if $id is a legit token , obtain corresponding session data
		if(!$this->users_model->check_token($token))
		{
			//not a legit token
			redirect('recovery');
			return;
		}
		else
		{
			$this->load->library('form_validation');
			$this->form_validation->set_error_delimiters('<p class="text-error">', '</p>');
			$this->form_validation->set_rules('password', 'New Password', 'required|min_length[8]');
			$this->form_validation->set_rules('confirm_password', 'Confirm Password', 'required|min_length[8]|matches[password]');
			
			//form validation
			if ($this->form_validation->run() == FALSE)
			{
				$this->load->view('templates/header', $data);
				$this->load->view('templates/navbar_login', $data);
				$this->load->view('new_password', $data);
				$this->load->view('templates/footer', $data);
				return;
			}
			else
			{
				$row = $this->users_model->get_user_by_token($token)->row();
				//update password in DB
				$this->users_model->reset_password($token);
				
				$this->session->set_userdata('userid', $row->id);
				$this->session->set_userdata('email', $row->email);
				$this->session->set_userdata('access', $row->access);
				redirect('dashboard');
				return;

			}

		}

	}

	private function _checklogin() {
		$this->load->library('session');

		$access = $this->session->userdata('access');
		if ( $access ) {
			// if logged in, redirect to dashboard
			redirect('dashboard');
			return false;
		}
		return true;
	}


	private function _loadview($data)
	{		
		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar_login', $data);
		$this->load->view('recovery', $data);
		$this->load->view('templates/footer', $data);
	}
}