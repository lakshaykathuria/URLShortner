async function shortenUrl() {
    const longUrl = document.getElementById("longUrl").value;
    const resultDiv = document.getElementById("result");

    if (!longUrl) {
        resultDiv.innerText = "Please enter a URL.";
        return;
    }

    try {
        const response = await fetch("/shorten", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ longUrl: longUrl }),
        });


        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Error shortening URL.");
        }

        const data = await response.json();
        resultDiv.innerHTML = `<a href="${data.shortUrl}" target="_blank">${data.shortUrl}</a>`;
    } catch (error) {
        resultDiv.innerText = "Error: " + error.message;
    }
}
