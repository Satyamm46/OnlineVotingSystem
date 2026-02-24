import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Register() {

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const register = async () => {
    try {

      const response = await axios.post("http://localhost:8080/auth/register", {
        name: name,
        email: email,
        password: password
      });

      setMessage(response.data);

      setTimeout(() => {
        navigate("/login");
      }, 1500);

    } catch (error) {
      setMessage("Registration Failed");
    }
  };

  return (
    <div>
      <h2>Register</h2>

      {message && <p style={{color: "green"}}>{message}</p>}

      <input
        type="text"
        placeholder="Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <br /><br />

      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <br /><br />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <br /><br />

      <button onClick={register}>Register</button>
    </div>
    
  );
}

export default Register;

// Add this in your register form
<select
  name="role"
  value={formData.role}
  onChange={handleChange}
>
  <option value="voter">Voter</option>
  <option value="admin">Admin</option>
</select>