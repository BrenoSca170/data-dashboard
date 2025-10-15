// Local: api-gateway/server.js

const express = require('express');
const cors = require('cors');
const multer = require('multer');
const axios = require('axios');
const FormData = require('form-data');
const bodyParser = require('body-parser');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const AUTH_SERVICE_URL = 'http://localhost:8081/api/auth';
// --- CONFIGURAÇÃO ---
const app = express();
const PORT = 3000;
const JWT_SECRET = 'allani-ama-o-luisinho'; // Mude isto para uma frase segura em produção

// --- MIDDLEWARES ---
app.use(cors());
app.use(bodyParser.json());

const storage = multer.memoryStorage();
const upload = multer({ storage: storage });

// ==============================================================================
//  ROTAS
// ==============================================================================

// Rota de teste inicial
app.get('/', (req, res) => {
    res.send('API Gateway está a funcionar!');
});
// ROTA DE REGISTO (PROXY)
app.post('/auth/register', async (req, res) => {
    try {
        // Apenas reencaminha o pedido para o serviço Spring Boot
        const response = await axios.post(`${AUTH_SERVICE_URL}/register`, req.body);
        // E devolve a resposta do serviço diretamente para o frontend
        res.status(response.status).json(response.data);
    } catch (error) {
        const status = error.response ? error.response.status : 500;
        const data = error.response ? error.response.data : { message: 'Erro interno no gateway.' };
        res.status(status).json(data);
    }
});
// ROTA DE LOGIN (PROXY)
app.post('/auth/login', async (req, res) => {
    try {
        // Apenas reencaminha o pedido para o serviço Spring Boot
        const response = await axios.post(`${AUTH_SERVICE_URL}/login`, req.body);
        // E devolve a resposta (com o token gerado pelo Spring) para o frontend
        res.status(response.status).json(response.data);
    } catch (error) {
        const status = error.response ? error.response.status : 401;
        const data = error.response ? error.response.data : { message: 'Erro interno no gateway.' };
        res.status(status).json(data);
    }
});

// MIDDLEWARE DE VERIFICAÇÃO DE TOKEN
function verifyToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];
    if (!token) {
        return res.status(403).json({ message: 'Token não fornecido.' });
    }
    jwt.verify(token, JWT_SECRET, (err, decoded) => {
        if (err) {
            return res.status(401).json({ message: 'Token inválido ou expirado.' });
        }
        req.user = decoded;
        next();
    });
}

// ROTA PROTEGIDA DE UPLOAD
app.post('/api/upload', verifyToken, upload.single('file'), async (req, res) => {
    if (!req.file) {
        return res.status(400).send({ message: 'Nenhum ficheiro enviado.' });
    }
    const formData = new FormData();
    formData.append('file', req.file.buffer, {
        filename: req.file.originalname,
        contentType: req.file.mimetype,
    });
    try {
        console.log(`Utilizador ${req.user.email} está a fazer upload de um ficheiro.`);
        const javaServiceUrl = 'http://localhost:8080/api/process-data';
        const response = await axios.post(javaServiceUrl, formData, {
            headers: { ...formData.getHeaders() },
        });
        res.status(200).json(response.data);
    } catch (error) {
        console.error('Erro ao chamar o serviço Java:', error.message);
        if (error.response) {
            return res.status(error.response.status).send(error.response.data);
        } else if (error.request) {
            return res.status(503).send({ message: 'O serviço de processamento está indisponível.' });
        } else {
            return res.status(500).send({ message: 'Erro interno no API Gateway.' });
        }
    }
});


// --- INICIALIZAÇÃO DO SERVIDOR ---
app.listen(PORT, () => {
    console.log(`API Gateway a rodar em http://localhost:${PORT}`);
});