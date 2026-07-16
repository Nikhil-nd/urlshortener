# URL Shortener

Professional URL Shortener with a Spring Boot backend and a React + Vite frontend. The application creates short links, supports optional custom aliases, redirects users to the original URL, and tracks click statistics.

## Features

- Shorten long URLs into compact shareable links.
- Create optional custom aliases when you want a memorable short link.
- Redirect visitors from the short code to the original URL.
- Track click counts for each shortened link.
- View link statistics through a simple API endpoint.
- Separate frontend and backend apps for easier development and deployment.

## Tech Stack

- Backend: Java 21, Spring Boot 3.4.4, Spring Web, Spring Data JPA, Spring Validation
- Database: MySQL
- Frontend: React 19, Vite, Axios, React Router
- Build Tools: Maven, npm

## Installation

### Prerequisites

- Java 21 or later
- Maven 3.9+ or the included Maven Wrapper
- Node.js 18+ and npm
- MySQL database

### Backend Setup

1. Clone the repository.
2. Create a MySQL database for the application.
3. Set the required environment variables:

```bash
DB_URL=jdbc:mysql://localhost:3306/urlshortener
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

4. Start the backend from the project root:

```bash
./mvnw spring-boot:run
```

On Windows, use:

```bash
mvnw.cmd spring-boot:run
```

### Frontend Setup

1. Open a new terminal in the `frontend` folder.
2. Install dependencies:

```bash
npm install
```

3. Start the development server:

```bash
npm run dev
```

## Usage

### Web UI

Open the frontend in your browser, enter a long URL, optionally provide a custom alias, and generate a short link.

### API Endpoints

#### Shorten a URL

```bash
POST /api/shorten
```

Example request:

```json
{
  "url": "https://example.com/some/very/long/link",
  "customAlias": "my-link"
}
```

Example response:

```json
{
  "shortUrl": "http://localhost:8080/my-link"
}
```

#### Redirect to the Original URL

```bash
GET /{shortCode}
```

This endpoint returns a `302 Found` response and redirects to the original URL.

#### Get Link Statistics

```bash
GET /api/stats/{shortCode}
```

Example response:

```json
{
  "originalUrl": "https://example.com/some/very/long/link",
  "shortCode": "my-link",
  "clickCount": 12
}
```

## Configuration

Backend configuration is stored in `src/main/resources/application.properties`.

- `DB_URL`: JDBC connection string for MySQL
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `app.base-url`: Base URL used when generating short links

Example local configuration:

```properties
app.base-url=http://localhost:8080/
```

Update `app.base-url` to match your deployed backend URL in production.

## Project Structure

```text
urlshortener/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── src/
│   ├── main/
│   │   ├── java/com/nd/urlshortener/
│   │   │   ├── Controller/
│   │   │   ├── Service/
│   │   │   ├── Repository/
│   │   │   ├── Entity/
│   │   │   ├── dto/
│   │   │   ├── config/
│   │   │   └── exception/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── frontend/
    ├── package.json
    ├── vite.config.js
    └── src/
        ├── App.jsx
        ├── main.jsx
        ├── components/
        ├── pages/
        ├── services/
        └── styles/
```

## Contributing

Contributions are welcome. If you plan to contribute:

1. Fork the repository.
2. Create a feature branch.
3. Make your changes.
4. Test both the backend and frontend locally.
5. Open a pull request with a clear summary of the update.

## License

MIT License

Copyright (c) 2026 [Your Name]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## Contact

Project maintainer: Nikhil

GitHub: [Nikhil-nd](https://github.com/Nikhil-nd)
