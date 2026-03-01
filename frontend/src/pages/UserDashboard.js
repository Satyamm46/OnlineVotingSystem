import Layout from "../components/Layout";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function UserDashboard() {
  const navigate = useNavigate();
  return (
    <Layout>
      <div className="dashboard-container">

  <h1 className="dashboard-title">Welcome Back 👋</h1>
  <p className="dashboard-subtitle">
    Participate in the election and explore AI-powered insights.
  </p>

  {/* Stats Section */}
  <div className="stats-grid">
    <div className="stat-card">
      <h3>🗳️ Total Leaders</h3>
      <p>7</p>
    </div>

    <div className="stat-card">
      <h3>🔥 Total Votes</h3>
      <p>10,000+</p>
    </div>

    <div className="stat-card">
      <h3>📅 Election Status</h3>
      <p>Active</p>
    </div>
  </div>

  {/* Action Cards */}
  <div className="action-grid">

    <div className="action-card" onClick={() => navigate("/vote")}>
      <div className="action-icon">🗳️</div>
      <h3>Vote Now</h3>
      <p>Cast your vote securely.</p>
    </div>

    <div className="action-card" onClick={() => navigate("/ai")}>
      <div className="action-icon">🤖</div>
      <h3>AI Recommendation</h3>
      <p>Get intelligent election insights.</p>
    </div>

  </div>

</div>
    </Layout>
  );
}

export default UserDashboard;