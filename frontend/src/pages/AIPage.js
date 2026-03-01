import { useState } from "react";
import api from "../api/api";
import Layout from "../components/Layout";
import ReactMarkdown from "react-markdown";

function AIPage() {

  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");

  const askAI = async () => {
    if (!question.trim()) return;

    try {
      const res = await api.post("/ai/ask", question, {
        headers: {
          "Content-Type": "text/plain"
        }
      });

      setAnswer(res.data);
    } catch (err) {
      console.error(err);
      setAnswer("AI Error. Check backend.");
    }
  };

  return (
    <Layout>
      <div className="ai-container">
        <h2 className="ai-title">AI Assistant</h2>

        <textarea
          className="ai-input"
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="Ask something..."
        />

        <button className="ai-button" onClick={askAI}>
          Ask AI
        </button>

        {answer && (
          <div className="ai-response-card">
            <div className="ai-response-title">AI Response</div>
            <div className="ai-response-text">
  <ReactMarkdown>{answer}</ReactMarkdown>
</div>
          </div>
        )}
      </div>
    </Layout>
  );
}

export default AIPage;