import { useState } from "react";
import api from "./services/api";
function App() {
  const [shortUrl, setShortUrl] = useState("");
  const [url, setUrl] = useState("");
  const [customAlias, setCustomAlias] = useState("");
  //
  const handleShorten = async () => {

    try {

        const response = await api.post("/shorten", {

            url,

            customAlias

        });

        setShortUrl(response.data.shortUrl);

    } catch (error) {

    if (error.response) {
        alert(error.response.data.message);
    } else {
        alert("Server is not running.");
    }

}

};

  return (
    <div style={{ width: "500px", margin: "50px auto" }}>

      <h1>URL Shortener</h1>

      <input
        type="text"
        placeholder="Enter URL"
        value={url}
        onChange={(e) => setUrl(e.target.value)}
      />

      <br /><br />

      <input
        type="text"
        placeholder="Custom Alias (Optional)"
        value={customAlias}
        onChange={(e) => setCustomAlias(e.target.value)}
      />

      <br /><br />

    <button
    onClick={handleShorten}
    disabled={!url.trim()}
>
    Shorten
</button>
{/* display the result */}
{
    shortUrl && (

        <div>

            <h3>Short URL</h3>

                  <a
          href={shortUrl}
          target="_blank"
          rel="noopener noreferrer"
      >

                {shortUrl}

            </a>

        </div>

    )
}

    </div>
  );
}

export default App;