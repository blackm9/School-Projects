<?php
class Users_model extends CI_Model {
	public function __construct()
	{
		parent::__construct();
		$this->load->database();
	}

	function add_user()
	{
		$data = array(
			'email' => $this->input->post('email'),
			'password' => crypt($this->input->post('password'), '$2y$07$y6WU7CYJwZqCADoR14gPGa$'),
			'access' => $this->input->post('add')
		);

		// default to student if not set (from signup page)
		if (!$data['access']) {
			$data['access'] = 'student';
		}

		$this->db->insert('users', $data);
		
		// fetch the new user
		$this->db->select('id, email, access');
		$this->db->where('email', $data['email']);
		$query = $this->db->get('users');
		return $query->row();
	}

	function get_user()
	{
		$this->db->select('id, email, password, access');
		$this->db->where('email', $this->input->post('email'));
		return $this->db->get('users');
	}

	function get_user_by_id($id)
  	{
		$this->db->select('id, email, access');
		$this->db->where('id', $id);
		return $this->db->get('users');
  	}

	function get_password_by_id($id)
	  {
	    $this->db->select('password');
	    $this->db->where('id', $id);
	    return $this->db->get('users');
	  }

	function get_user_by_token($token){
		$this->db->select('id, email, access');
		$this->db->where('reset_token', $token);
		return $this->db->get('users');
	}

	function get_users()
	{
		$this->db->select('id, email, access');
		$this->db->order_by('email', 'asc');
		$query = $this->db->get('users');
		return $query->result();
	}

	function get_teachers()
	{
		$this->db->where('access', 'teacher');
		return $this->get_users();
	}

	function get_graders()
	{
		$this->db->where('access', 'grader');
		return $this->get_users();
	}

	function get_students()
	{
		$this->db->where('access', 'student');
		return $this->get_users();
	}

	function delete_user($id)
	{
		$this->db->delete('users', array('id' => $id)); 
	}

	function set_access($id, $access)
	{
		$data = array('access' => $access);
		$this->db->where('id', $id);
		$this->db->update('users', $data); 
	}

	function change_password($id)
	{
		$data = array(
			'password' => $this->hash_password($this->input->post('inputPassword')),
		);

		$this->db->where('id', $id);
		$this->db->update('users', $data); 
	}

	function generate_token($length = 32) 
	{
	    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	    $randomString = '';
	    for ($i = 0; $i < $length; $i++) 
	    {
	        $randomString .= $characters[rand(0, strlen($characters) - 1)];
	    }
	    return $randomString;
	}

	function set_reset_token($email)
	{
		$random_token = $this->generate_token();
		$data = array('reset_token' => $random_token);
		$this->db->where('email', $email);
		$this->db->update('users', $data); 
		return $random_token;
	}

	function check_token($token)
	{
		$this->db->where('reset_token', $token);
		$this->db->from('users');
		return ($this->db->count_all_results() > 0);
	}


	function reset_password($token)
	{
		$data = array(
			'password' => $this->hash_password($this->input->post('password')),
			'reset_token' => 'NULL'
		);

		$this->db->where('reset_token', $token);
		$this->db->update('users', $data); 
	}

	function hash_password($password)
	{
		$random_salt = $this->generate_token(22);
		$random_salt = '$2y$07$' . $random_salt . '$';
		return crypt($this->input->post('password'), $random_salt);
	}


}