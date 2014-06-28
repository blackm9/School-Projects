<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
class Logout extends CI_Controller {
	
	public function index()
	{
		// destroy the session
		$this->load->library('session');
		$this->session->sess_destroy();
		// bounce to login page
		redirect('login');
	}
}
?>