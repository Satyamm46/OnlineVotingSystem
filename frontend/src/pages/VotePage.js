import { useState, useEffect } from "react";
import api from "../api/api";
import Layout from "../components/Layout";
import jsPDF from "jspdf";

function VotePage() {

  const [leaders, setLeaders] = useState([]);
  const [slip, setSlip] = useState(null);
  const [loading, setLoading] = useState(false);
  const [hasVoted, setHasVoted] = useState(false);

  // ✅ Load leaders + vote status on page load
  useEffect(() => {
    fetchLeaders();
    checkVoteStatus();
  }, []);

  const checkVoteStatus = async () => {
    try {
      const res = await api.get("/vote/status");
      setHasVoted(res.data);
    } catch (err) {
      console.error("Error checking vote status");
    }
  };

  const fetchLeaders = async () => {
    try {
      const res = await api.get("/leader/all");
      setLeaders(res.data);
    } catch (err) {
      console.error("Error fetching leaders");
    }
  };

  const voteLeader = async (id) => {
    try {
      setLoading(true);
      const res = await api.post(`/vote/${id}`);
      setSlip(res.data);
      setHasVoted(true);
      setLoading(false);
    } catch (err) {
      setLoading(false);
      alert("Error voting");
    }
  };

  // ✅ Download Voting Slip
  const downloadSlip = () => {
    const doc = new jsPDF();

    doc.text("Voting Slip", 20, 20);
    doc.text(`Vote ID: ${slip.voteId}`, 20, 30);
    doc.text(`Voter: ${slip.voterEmail}`, 20, 40);
    doc.text(`Leader: ${slip.leaderName}`, 20, 50);
    doc.text(`Party: ${slip.party}`, 20, 60);
    doc.text(`Aadhaar: ${slip.aadhaar}`, 20, 70);
    doc.text(`Date: ${slip.votedAt}`, 20, 80);

    doc.save("VotingSlip.pdf");
  };

  return (
    <Layout>
      <div className="max-w-6xl mx-auto p-6">

        <h2 className="text-3xl font-bold mb-8 text-center">
          Vote for Your Leader
        </h2>

        {/* ✅ If already voted */}
        {hasVoted && !slip && (
          <div className="text-center text-red-600 font-semibold text-xl">
            You have already voted. Voting option disabled.
          </div>
        )}

        {/* ✅ Leader Cards */}
        {!hasVoted && !slip && (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
            {leaders.map((leader) => (
              <div
                key={leader.id}
                className="bg-white shadow-lg rounded-xl p-6 text-center"
              >

                {/* ✅ Show Party Logo if exists */}
                {leader.logo && (
                  <img
                    src={`http://localhost:8080/uploads/${leader.logo}`}
                    alt="Party Logo"
                    className="w-20 h-20 mx-auto object-contain mb-4"
                  />
                )}

                <h3 className="text-xl font-semibold">
                  {leader.name}
                </h3>

                <p className="text-gray-600">
                  {leader.party}
                </p>

                <button
                  onClick={() => voteLeader(leader.id)}
                  disabled={loading}
                  className="mt-4 w-full bg-green-500 text-white py-2 rounded-lg hover:bg-green-600"
                >
                  {loading ? "Submitting..." : "Vote"}
                </button>
              </div>
            ))}
          </div>
        )}

        {/* ✅ Voting Slip */}
        {slip && (
          <div className="mt-10 bg-white shadow-xl p-6 rounded-xl max-w-md mx-auto text-center">

            <h3 className="text-2xl font-bold text-green-600 mb-4">
              ✅ Vote Successful
            </h3>

            <div className="text-left space-y-2">
              <p><strong>Vote ID:</strong> {slip.voteId}</p>
              <p><strong>Voter:</strong> {slip.voterEmail}</p>
              <p><strong>Leader:</strong> {slip.leaderName}</p>
              <p><strong>Party:</strong> {slip.party}</p>
              <p><strong>Aadhaar:</strong> {slip.aadhaar}</p>
              <p><strong>Date:</strong> {slip.votedAt}</p>
            </div>

            <button
              onClick={downloadSlip}
              className="mt-6 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
            >
              Download Slip
            </button>

          </div>
        )}

      </div>
    </Layout>
  );
}

export default VotePage;