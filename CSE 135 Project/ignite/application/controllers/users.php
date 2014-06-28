<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');
class Users extends CI_Controller {
	
	public function __construct()
	{
		parent::__construct();
		$this->load->model('users_model');
	}

	public function index()
	{
		// make sure we are logged in as an admin
		if (!$this->_checklogin())
			return;

		$this->load->library('form_validation');

		$this->form_validation->set_error_delimiters('<p class="text-error">', '</p>');
		$this->form_validation->set_message('is_unique', 'There is already an account for that Email.');

		$this->form_validation->set_rules('email', 'Email address', 'required|valid_email|is_unique[users.email]');
		$this->form_validation->set_rules('password', 'Password', 'required|min_length[8]');

		$data['title'] = 'Quiz Maker - User Management';
		$data['email'] = $this->session->userdata('email');
		$data['access'] = $this->session->userdata('access');

		if ($this->form_validation->run())
		{
			$this->users_model->add_user();
		}

		$data['users'] = array_merge(
			$this->users_model->get_teachers(),
	  		$this->users_model->get_graders(),
	    	$this->users_model->get_students()
	    );
		$this->_loadview($data);
	}

public function edit($id)
  {
    // make sure we are logged in as an admin
    if (!$this->_checklogin())
      return;

    // get the form button if one posted
    $edit = $this->input->post('edit');
    switch ($edit)
    {
      case 'teacher':
      case 'grader':
      case 'student':
        $this->users_model->set_access($id, $edit);
      case 'cancel':
        redirect('users');
        return;
        break;
    }

    // get the user we are editing
    $query = $this->users_model->get_user_by_id($id);
    if ($query->num_rows() == 0) {
      // there is not a login associated with this email
      redirect('users');
      return;
    }
    $data['user'] = $query->row();
    $data['title'] = 'Quiz Maker - User Management';
    $data['email'] = $this->session->userdata('email');
    $data['access'] = $this->session->userdata('access');


    $this->load->view('templates/header', $data);
    $this->load->view('templates/navbar', $data);
    $this->load->view('edit_user', $data);
    $this->load->view('templates/footer', $data);
  }


	public function delete($id)
	{
		// make sure we are logged in as an admin
		if (!$this->_checklogin())
			return;

		// delete the user
		$this->users_model->delete_user($id);
		// bounce to user management
		redirect('users');
	}

	private function _checklogin() {
		$this->load->library('session');

		$access = $this->session->userdata('access');
		if ( !$access ) {
			// not logged in, bounce to login
			redirect('login');
			return false;
		}
		else if ( $access != 'teacher' ) {
			// not an admin, bounce to dashboard
			redirect('dashboard');
			return false;
		}
		return true;
	}

	private function _loadview($data)
	{		
		$this->load->view('templates/header', $data);
		$this->load->view('templates/navbar', $data);
		$this->load->view('users', $data);
		$this->load->view('templates/footer', $data);
	}
}