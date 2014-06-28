<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
class Settings extends CI_Controller {
	
	public function __construct()
	{
		parent::__construct();
		$this->load->model('users_model');
	}

	public function index()
	{
		// make sure we are logged in 
		if (!$this->_checklogin())
			return;

		$this->load->library('form_validation');

		$this->form_validation->set_error_delimiters('<p class="text-error">', '</p>');

		$this->form_validation->set_rules('oldPassword', 'Old Password', 'required|min_length[8]');
		$this->form_validation->set_rules('inputPassword', 'New Password', 'required|min_length[8]');
		$this->form_validation->set_rules('confirmPassword', 'Confirm Password', 'required|min_length[8]|matches[inputPassword]');

		$data['title'] = 'Quiz Maker - Settings';
		$data['email'] = $this->session->userdata('email');
		$data['access'] = $this->session->userdata('access');

		if ($this->form_validation->run())
		{

			// find user password for this id
			$query = $this->users_model->get_password_by_id($this->session->userdata('userid'));
			if ($query->num_rows() == 0) {
				// there is not a login associated with this id
				redirect('logout');
				return;
			}

			// verify password matches			
			$row = $query->row();
			if (crypt($this->input->post('oldPassword'), $row->password) == $row->password)
			{
				$this->users_model->change_password($this->session->userdata('userid'));
				$data['changed']=true;
			}
			else
			{
				$data['wrongpassword'] = true;
			}
		}

		$this->_loadview($data);
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

	private function _loadview($data)
	{		
		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar', $data);
		$this->load->view('settings', $data);
		$this->load->view('templates/footer', $data);
	}
}