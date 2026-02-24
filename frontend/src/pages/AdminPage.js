import React, { useState, useEffect } from "react";
import api from "../services/api";

function AdminPage() {
  const [leaders, setLeaders] = useState([]);
  const [name, setName] = useState("");

  useEffect(() => {
    fetchLeaders();
  }, []);

  const fetchLeaders = async () => {
    const res = await api.get("/leaders");
    setLeaders(res.data);
  };

  const addLeader = async () => {
    await api.post("/leaders", { name });
    setName("");
    fetchLeaders();
  };

  const deleteLeader = async (id) => {
    await api.delete(`/leaders/${id}`);
    fetchLeaders();
  };

  return (
    <div className="admin-container">
      <h2>Admin Panel</h2>

      <input
        type="text"
        placeholder="Leader Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <button onClick={addLeader}>Add Leader</button>

      <ul>
        {leaders.map((leader) => (
          <li key={leader._id}>
            {leader.name}
            <button onClick={() => deleteLeader(leader._id)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default AdminPage;