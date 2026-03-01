import { useState } from "react";
import api from "../api/api";
import Layout from "../components/Layout";

function Register() {

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [aadhaar, setAadhaar] = useState("");
  const [otp, setOtp] = useState("");
  const [otpSent, setOtpSent] = useState(false);
  const [message, setMessage] = useState("");

  // 🔥 SEND OTP
  const sendOtp = async () => {
    try {
      const res = await api.post("/auth/send-otp", {
        name,
        email,
        password,
        aadhaarNumber: aadhaar
      });

      setMessage(res.data);
      setOtpSent(true);

    } catch (err) {
      setMessage(err.response?.data || "Error sending OTP");
    }
  };

  // 🔥 VERIFY OTP
  const verifyOtp = async () => {
    try {
      const res = await api.post("/auth/verify-otp", {
        email,
        otp
      });

      setMessage(res.data);

    } catch (err) {
      setMessage(err.response?.data || "Invalid OTP");
    }
  };

  return (
    <Layout>
      <div className="max-w-md mx-auto mt-10 p-6 bg-white shadow rounded">

        <h2 className="text-2xl font-bold mb-4 text-center">
          Register
        </h2>

        <input
          type="text"
          placeholder="Full Name"
          className="border p-2 w-full mb-3"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <input
          type="email"
          placeholder="Email"
          className="border p-2 w-full mb-3"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          className="border p-2 w-full mb-3"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <input
          type="text"
          placeholder="12-digit Aadhaar"
          className="border p-2 w-full mb-3"
          value={aadhaar}
          maxLength="12"
          onChange={(e) => {
            const value = e.target.value.replace(/\D/g, "");
            setAadhaar(value);
          }}
        />

        {/* 🔥 SEND OTP BUTTON */}
        {!otpSent && (
          <button
            onClick={sendOtp}
            className="bg-blue-500 text-white px-4 py-2 w-full rounded"
          >
            Send OTP
          </button>
        )}

        {/* 🔥 OTP FIELD */}
        {otpSent && (
          <>
            <input
              type="text"
              placeholder="Enter OTP"
              className="border p-2 w-full mt-4"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
            />

            <button
              onClick={verifyOtp}
              className="bg-green-500 text-white px-4 py-2 w-full mt-2 rounded"
            >
              Verify OTP
            </button>
          </>
        )}

        {message && (
          <p className="mt-4 text-center text-red-500">
            {message}
          </p>
        )}

      </div>
    </Layout>
  );
}

export default Register;