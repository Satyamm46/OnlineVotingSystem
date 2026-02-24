import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";

function AIRecommendation() {
  const [leader, setLeader] = useState(null);
  const navigate = useNavigate();

  const getRecommendation = async () => {
    const res = await API.post("/ai/recommend?w1=5&w2=4&w3=3");
    setLeader(res.data);
  };

  return (
    <div style={{ padding: "30px" }}>
      <h2>AI Recommendation</h2>

      <button onClick={getRecommendation}>Get Best Leader</button>

      {leader && (
        <div style={{ border: "1px solid gray", padding: "10px", margin: "10px" }}>
          <h3>{leader.name}</h3>
          <p>Party: {leader.party}</p>
          <p>Experience: {leader.experience}</p>
          <p>Rating: {leader.rating}</p>
          <p>Achievements: {leader.achievements}</p>
        </div>
      )}

      <br />
      <button onClick={() => navigate("/dashboard")}>Back</button>
    </div>
  );
}

export default AIRecommendation;
