import { useEffect, useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const [leaders, setLeaders] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchLeaders();
  }, []);

  const fetchLeaders = async () => {
    const res = await API.get("/leader/all");
    setLeaders(res.data);
  };

  const vote = async (id) => {
    try {
      await API.post(`/vote/${id}`);
      alert("Vote Cast Successfully");
    } catch {
      alert("You already voted");
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div style={{ padding: "30px" }}>
      <h2>Leaders</h2>

      {leaders.map(l => (
        <div key={l.id} style={{ border: "1px solid gray", padding: "10px", margin: "10px" }}>
          <h3>{l.name}</h3>
          <p>Party: {l.party}</p>
          <p>Experience: {l.experience}</p>
          <p>Rating: {l.rating}</p>
          <button onClick={() => vote(l.id)}>Vote</button>
        </div>
      ))}

      <br />
      <button onClick={() => navigate("/ai")}>AI Recommendation</button>
      <br /><br />
      <button onClick={logout}>Logout</button>
    </div>
  );
}

export default Dashboard;
