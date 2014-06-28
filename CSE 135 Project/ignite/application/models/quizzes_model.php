<?php
class Quizzes_model extends CI_Model {
	public function __construct()
	{
		parent::__construct();
		$this->load->database();
	}

	// inserts the quiz if it's new, or updates the existing one if it already exists
	function replace_quiz()
	{
		// get the data (already validated & sanitized)
		$data['id'] = $this->input->post('id');
		$data['name'] = $this->input->post('name');
		$data['points'] = $this->input->post('points');
		$data['open'] = $this->input->post('open');
		$data['due'] = $this->input->post('due');
		$data['data'] = $this->input->post('data');

		$this->db->where('id', $data['id']);
		$this->db->from('quizzes');
		$update = ($this->db->count_all_results() > 0);

		if ($update)
		{
			$this->db->where('id', $data['id']);
			$this->db->update('quizzes', $data);
		}
		else
		{
			$this->db->insert('quizzes', $data);
		}
	}

	function insert_submission($userid)
	{
		// get the data (already validated & sanitized)
		$data['quizid'] = $this->input->post('id');
		$data['data'] = $this->input->post('data');
		$data['userid'] = $userid;

		$this->db->insert('submissions', $data);
	}

	function update_submission($id, $currentstatus, $newstatus)
	{
		// get the data (already validated & sanitized)
		$data['data'] = $this->input->post('data');
		$data['grade'] = $this->input->post('grade');
		$data['status'] = $newstatus;
		$this->db->where('id', $id);
		// don't update one that is in wrong state
		$this->db->where('status', $currentstatus);
		$this->db->update('submissions', $data);
	}

	// uses userid + quizid combo instead of submission id
	function update_submission_by_id_pair($userid, $quizid, $currentstatus, $newstatus)
	{
		// get the data (already validated & sanitized)
		$data['data'] = $this->input->post('data');
		$data['status'] = $newstatus;
		$this->db->where('userid', $userid);
		$this->db->where('quizid', $quizid);
		// don't update one that is in wrong state
		$this->db->where('status', $currentstatus);
		$this->db->update('submissions', $data);
	}

	function get_quizzes()
	{
		$this->db->select('id, name');
		$query = $this->db->get('quizzes');
		return $query->result();
	}

	function has_taken($userid, $quizid)
	{
		$this->db->where('userid', $userid);
		$this->db->where('quizid', $quizid);
		$this->db->from('submissions');
		return ($this->db->count_all_results() > 0);
	}

	function get_quiz($id)
	{
		$this->db->select('data');
		$this->db->where('id', $id);
		return $this->db->get('quizzes');
	}

	function get_pending($userid)
	{
		$this->db->select('quizzes.id AS id, name, points, due');
		$this->db->join("(SELECT * FROM submissions WHERE userid = $userid) AS s", 's.quizid = quizzes.id', 'left outer');
		$this->db->where('status IS NULL');
		$this->db->order_by('due', 'asc');
		$query = $this->db->get('quizzes');
		return $query->result();
	}

	function get_completed($userid)
	{
		$this->db->join('quizzes', 'submissions.quizid = quizzes.id');
		$this->db->select('submissions.id AS id, name, grade, points, due, status');
		$this->db->where('userid', $userid);
		$this->db->order_by('due', 'asc');
		$query = $this->db->get('submissions');
		return $query->result();
	}

	function get_submission($id, $status)
	{
		$this->db->select('data');
		$this->db->where('id', $id);
		$this->db->where('status', $status);
		return $this->db->get('submissions');
	}

	function get_submissions()
	{
		$this->db->select('submissions.id AS id, name, email, status, due');
		$this->db->join('quizzes', 'submissions.quizid = quizzes.id');
		$this->db->join('users', 'submissions.userid = users.id');
		$this->db->where('status !=', 'Graded');
		$this->db->where('status !=', 'Graded -- Regrade Complete');
		$this->db->order_by('due', 'asc');
		$query = $this->db->get('submissions');
		return $query->result();
	}
}