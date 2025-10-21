import { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [pessoas, setPessoas] = useState([]);
  const [form, setForm] = useState({ nome: "", idade: "", cpf: "" });
  const [editando, setEditando] = useState(null);

  const API_URL = "http://localhost:8080/pessoa"; // sua API Spring Boot

  // Buscar todas as pessoas
  const carregarPessoas = async () => {
    const resp = await fetch(API_URL);
    const data = await resp.json();
    setPessoas(data);
  };

  useEffect(() => {
    carregarPessoas();
  }, []);

  // Cadastrar ou atualizar pessoa
  const salvarPessoa = async (e) => {
    e.preventDefault();

    const metodo = editando ? "PUT" : "POST";
    const url = editando ? `${API_URL}/${editando.id}` : API_URL;

    await fetch(url, {
      method: metodo,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    });

    setForm({ nome: "", idade: "", cpf: "" });
    setEditando(null);
    carregarPessoas();
  };

  // Excluir pessoa
  const excluirPessoa = async (id) => {
    await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    carregarPessoas();
  };

  // Preparar edição
  const editarPessoa = (p) => {
    setForm({ nome: p.nome, idade: p.idade, cpf: p.cpf });
    setEditando(p);
  };

  return (
    <div
      style={{ maxWidth: 700, margin: "40px auto", fontFamily: "sans-serif" }}
    >
      <h1>Gerenciar Pessoas</h1>

      <form onSubmit={salvarPessoa} style={{ marginBottom: 20 }}>
        <input
          type="text"
          placeholder="Nome"
          value={form.nome}
          onChange={(e) => setForm({ ...form, nome: e.target.value })}
          required
        />
        <input
          type="number"
          placeholder="Idade"
          value={form.idade}
          onChange={(e) => setForm({ ...form, idade: e.target.value })}
          required
        />
        <input
          type="text"
          placeholder="CPF"
          value={form.cpf}
          onChange={(e) => setForm({ ...form, cpf: e.target.value })}
          required
        />
        <button type="submit" id="">
          {editando ? "Atualizar" : "Cadastrar"}
        </button>
        {editando && (
          <button
            type="button"
            onClick={() => {
              setForm({ nome: "", idade: "", cpf: "" });
              setEditando(null);
            }}
          >
            Cancelar
          </button>
        )}
      </form>

      <table
        border="1"
        cellPadding="8"
        style={{ width: "100%", borderCollapse: "collapse" }}
      >
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Idade</th>
            <th>CPF</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {pessoas.length > 0 ? (
            pessoas.map((p) => (
              <tr key={p.id}>
                <td>{p.id}</td>
                <td>{p.nome}</td>
                <td>{p.idade}</td>
                <td>{p.cpf}</td>
                <td>
                  <button onClick={() => editarPessoa(p)}>Editar</button>
                  <button onClick={() => excluirPessoa(p.id)}>Excluir</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="5" align="center">
                Nenhuma pessoa cadastrada
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default App;
