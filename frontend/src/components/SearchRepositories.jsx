import { useState } from "react";
import axios from "axios";

function SearchRepositories() {

    const [query, setQuery] = useState("");
    const [repositories, setRepositories] = useState([]);

    const searchRepositories = async () => {

        try {

            const response = await axios.get(
                `https://reposense-1.onrender.com/api/github/search?query=${query}`
            );

            setRepositories(response.data);

        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div style={{ padding: "20px" }}>

            <h1>RepoSense</h1>

            <input
                type="text"
                placeholder="Search repositories..."
                value={query}
                onChange={(e) => setQuery(e.target.value)}
            />

            <button onClick={searchRepositories}>
                Search
            </button>

            <div>

                {repositories.map((repo, index) => (

                    <div
                        key={index}
                        style={{
                            border: "1px solid gray",
                            marginTop: "10px",
                            padding: "10px"
                        }}
                    >

                        <h3>{repo.repoName}</h3>

                        <p>{repo.description}</p>

                        <p>
                            Owner: {repo.owner}
                        </p>

                        <p>
                            Language: {repo.language}
                        </p>

                        <p>
                            Stars: {repo.stars}
                        </p>

                    </div>
                ))}
            </div>
        </div>
    );
}

export default SearchRepositories;