import { useState, useEffect } from "react";
import api from "../api/api";
import Layout from "../components/Layout";

function AdminDashboard() {

  const [leaders, setLeaders] = useState([]);
  const [name, setName] = useState("");
  const [party, setParty] = useState("");
  const [logo, setLogo] = useState(null);
  const [preview, setPreview] = useState(null);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchLeaders();
  }, []);

  const fetchLeaders = async () => {
    try {
      const res = await api.get("/leader/all");
      setLeaders(res.data);
    } catch (err) {
      setMessage("Error loading leaders");
    }
  };

  const addLeader = async () => {
    if (!name || !party) {
      setMessage("Name and Party are required");
      return;
    }

    try {
      setLoading(true);

      const res = await api.post("/admin/leader/add", {
        name,
        party
      });

      const leaderId = res.data.id;

      if (logo) {
        const formData = new FormData();
        formData.append("file", logo);

        await api.post(
          `/admin/leader/${leaderId}/upload-logo`,
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data"
            }
          }
        );
      }

      setName("");
      setParty("");
      setLogo(null);
      setPreview(null);
      setMessage("Leader added successfully ✅");
      fetchLeaders();
      setLoading(false);

    } catch (err) {
      setLoading(false);
      setMessage("Error adding leader ❌");
    }
  };

  const deleteLeader = async (id) => {
    try {
      await api.delete(`/admin/leader/delete/${id}`);
      setMessage("Leader deleted successfully 🗑️");
      fetchLeaders();
    } catch (err) {
      setMessage("Error deleting leader ❌");
    }
  };

  return (
    <Layout>
      <div className="max-w-6xl mx-auto p-6">

        <h2 className="text-3xl font-bold mb-8 text-center">
          Admin Dashboard
        </h2>

        {/* ADD LEADER SECTION */}
        <div className="bg-white shadow-lg rounded-xl p-6 mb-10">

          <h3 className="text-xl font-semibold mb-4">
            Add Leader
          </h3>

          <div className="flex flex-wrap gap-4 items-center">

            <input
              className="border p-2 rounded"
              placeholder="Leader Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />

            <input
              className="border p-2 rounded"
              placeholder="Party Name"
              value={party}
              onChange={(e) => setParty(e.target.value)}
            />

            <input
              type="file"
              accept="image/*"
              onChange={(e) => {
                const file = e.target.files[0];
                setLogo(file);
                setPreview(URL.createObjectURL(file));
              }}
            />

            <button
              onClick={addLeader}
              disabled={loading}
              className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            >
              {loading ? "Adding..." : "Add Leader"}
            </button>

          </div>

          {/* MESSAGE DISPLAY */}
          {message && (
            <div className="mt-4 text-center font-semibold text-blue-600">
              {message}
            </div>
          )}

          {/* LOGO PREVIEW */}
          {preview && (
            <div className="mt-4">
              <p className="text-sm text-gray-500">Logo Preview:</p>
              <img
                src={preview}
                alt="Preview"
                className="w-24 h-24 object-contain mt-2"
              />
            </div>
          )}

        </div>

        {/* LEADER LIST */}
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          {leaders.map((leader) => (
            <div
              key={leader.id}
              className="bg-white shadow-lg rounded-xl p-6 text-center"
            >

              {leader.logo && (
                <img
                  src={`http://localhost:8080/uploads/${leader.logo}`}
                  alt="Logo"
                  className="w-20 h-20 mx-auto object-contain mb-3"
                />
              )}

              <h3 className="text-lg font-semibold">
                {leader.name}
              </h3>

              <p className="text-gray-600">
                {leader.party}
              </p>

              {/* ✅ VOTE COUNT */}
              <p className="mt-2 font-bold text-green-600">
                Votes: {leader.votes}
              </p>

              {/* ✅ DELETE BUTTON */}
              <button
                onClick={() => deleteLeader(leader.id)}
                className="mt-3 bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
              >
                Delete
              </button>

            </div>
          ))}
        </div>

      </div>
    </Layout>
  );
}

export default AdminDashboard;