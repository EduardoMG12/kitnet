'use client';

import React, { useState } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Badge } from "@/components/ui/badge";
import { PiggyBank, AlertTriangle, CheckCircle, Calculator } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";

const RentBudgetPage = () => {
    const [rendaBruta, setRendaBruta] = useState("");
    const [gastosFixos, setGastosFixos] = useState("");
    const [dependentes, setDependentes] = useState("0");
    const [tempoContrato, setTempoContrato] = useState("12");
    interface Resultado {
        aluguelMaximo: number;
        rendaLiquida: number;
        classificacao: string;
        cor: string;
        faixas: {
            nome: string;
            valor: number;
            descricao: string;
        }[];
        porcentagemRenda: string;
    }

    const [resultado, setResultado] = useState<Resultado | null>(null);

    const calcular = () => {
        const renda = Number.parseFloat(rendaBruta) || 0;
        const gastos = Number.parseFloat(gastosFixos) || 0;
        const numDependentes = Number.parseInt(dependentes) || 0;

        // Renda líquida aproximada
        const rendaLiquida = renda - gastos;

        // Regra dos 30% - máximo recomendado para aluguel
        const aluguelMaximo = rendaLiquida * 0.3;

        // Considerando gastos adicionais (condomínio, IPTU, etc.)
        const aluguelComGastos = aluguelMaximo * 0.75; // 75% do máximo para gastos extras

        // Ajuste por dependentes
        const ajusteDependentes = Math.max(0, numDependentes - 1) * 0.05;
        const aluguelAjustado = aluguelComGastos * (1 - ajusteDependentes);

        // Classificação de renda
        let classificacao = "";
        let cor = "";
        if (renda < 2000) {
            classificacao = "Baixa Renda";
            cor = "red";
        } else if (renda < 5000) {
            classificacao = "Classe C";
            cor = "yellow";
        } else if (renda < 10000) {
            classificacao = "Classe B";
            cor = "blue";
        } else {
            classificacao = "Classe A";
            cor = "green";
        }

        // Recomendações por faixa
        const faixas = [
            {
                nome: "Econômico",
                valor: aluguelAjustado * 0.7,
                descricao: "Imóveis básicos, pode precisar de reformas"
            },
            {
                nome: "Padrão",
                valor: aluguelAjustado,
                descricao: "Imóveis em bom estado, boa localização"
            },
            {
                nome: "Superior",
                valor: aluguelAjustado * 1.2,
                descricao: "Imóveis novos ou reformados, ótima localização"
            }
        ];

        setResultado({
            aluguelMaximo: aluguelAjustado,
            rendaLiquida,
            classificacao,
            cor,
            faixas,
            porcentagemRenda: (aluguelAjustado / rendaLiquida * 100).toFixed(1)
        });
    };

    return (
        <MainLayout>
            <div className="py-16 bg-gradient-to-b from-[#f9f4e8] to-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="text-center mb-12">
                        <h1 className="text-4xl md:text-5xl font-heading font-bold mb-6">
                            Que Aluguel Cabe no Meu Bolso?
                        </h1>
                        <p className="text-xl text-muted-foreground max-w-3xl mx-auto">
                            Descubra qual valor de aluguel é adequado para sua renda e situação financeira
                        </p>
                    </div>

                    <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                        <Card>
                            <CardHeader>
                                <div className="flex items-center gap-3">
                                    <Calculator className="h-6 w-6 text-[#e56b4e]" />
                                    <CardTitle>Calculadora de Orçamento</CardTitle>
                                </div>
                                <CardDescription>
                                    Preencha seus dados financeiros para calcular o aluguel ideal
                                </CardDescription>
                            </CardHeader>
                            <CardContent className="space-y-6">
                                <div>
                                    <Label htmlFor="renda">Renda Bruta Mensal (R$)</Label>
                                    <Input
                                        id="renda"
                                        type="number"
                                        placeholder="Ex: 5000"
                                        value={rendaBruta}
                                        onChange={(e) => setRendaBruta(e.target.value)}
                                    />
                                </div>

                                <div>
                                    <Label htmlFor="gastos">Gastos Fixos Mensais (R$)</Label>
                                    <Input
                                        id="gastos"
                                        type="number"
                                        placeholder="Ex: 1500"
                                        value={gastosFixos}
                                        onChange={(e) => setGastosFixos(e.target.value)}
                                    />
                                    <p className="text-sm text-muted-foreground mt-1">
                                        Inclua: alimentação, transporte, saúde, educação, empréstimos
                                    </p>
                                </div>

                                <div>
                                    <Label htmlFor="dependentes">Número de Dependentes</Label>
                                    <Select value={dependentes} onValueChange={setDependentes}>
                                        <SelectTrigger>
                                            <SelectValue />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="0">Nenhum</SelectItem>
                                            <SelectItem value="1">1 dependente</SelectItem>
                                            <SelectItem value="2">2 dependentes</SelectItem>
                                            <SelectItem value="3">3 dependentes</SelectItem>
                                            <SelectItem value="4">4+ dependentes</SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>

                                <div>
                                    <Label htmlFor="contrato">Tempo de Contrato Desejado</Label>
                                    <Select value={tempoContrato} onValueChange={setTempoContrato}>
                                        <SelectTrigger>
                                            <SelectValue />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="6">6 meses</SelectItem>
                                            <SelectItem value="12">12 meses</SelectItem>
                                            <SelectItem value="24">24 meses</SelectItem>
                                            <SelectItem value="36">36 meses</SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>

                                <Button onClick={calcular} className="w-full bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                    <PiggyBank className="mr-2 h-4 w-4" />
                                    Calcular Orçamento
                                </Button>
                            </CardContent>
                        </Card>

                        {resultado && (
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-3">
                                        <CheckCircle className="h-6 w-6 text-green-600" />
                                        Seu Orçamento para Aluguel
                                    </CardTitle>
                                    <div className="flex gap-2">
                                        <Badge variant={resultado.cor === "green" ? "default" : resultado.cor === "blue" ? "secondary" : "outline"}>
                                            {resultado.classificacao}
                                        </Badge>
                                        <Badge variant="outline">
                                            {resultado.porcentagemRenda}% da renda líquida
                                        </Badge>
                                    </div>
                                </CardHeader>
                                <CardContent className="space-y-6">
                                    <div className="text-center bg-[#e56b4e]/5 p-6 rounded-lg">
                                        <h3 className="text-lg font-semibold mb-2">Valor Máximo Recomendado</h3>
                                        <p className="text-3xl font-bold text-[#e56b4e]">
                                            R$ {resultado.aluguelMaximo.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                        </p>
                                        <p className="text-sm text-muted-foreground mt-2">
                                            Incluindo condomínio e IPTU
                                        </p>
                                    </div>

                                    <div>
                                        <h3 className="font-semibold mb-3">Faixas de Imóveis Recomendadas</h3>
                                        <div className="space-y-3">
                                            {resultado.faixas.map((faixa: { nome: string; valor: number; descricao: string; }, index: number) => (
                                                <div key={faixa.nome} className="border rounded-lg p-3">
                                                    <div className="flex justify-between items-center mb-1">
                                                        <span className="font-semibold">{faixa.nome}</span>
                                                        <span className="font-bold text-[#e56b4e]">
                                                            R$ {faixa.valor.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                                        </span>
                                                    </div>
                                                    <p className="text-sm text-muted-foreground">{faixa.descricao}</p>
                                                </div>
                                            ))}
                                        </div>
                                    </div>

                                    <div className="bg-yellow-50 p-4 rounded-lg">
                                        <div className="flex items-start gap-2">
                                            <AlertTriangle className="h-5 w-5 text-yellow-600 mt-0.5" />
                                            <div>
                                                <h4 className="font-semibold text-yellow-800 mb-1">Dicas Importantes</h4>
                                                <ul className="text-sm text-yellow-700 space-y-1">
                                                    <li>• Reserve 10% da renda para emergências</li>
                                                    <li>• Considere custos de mudança e mobiliário</li>
                                                    <li>• Negocie prazos e condições do contrato</li>
                                                    <li>• Verifique a documentação do imóvel</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>
                        )}
                    </div>

                    <Card className="mt-8">
                        <CardHeader>
                            <CardTitle>Regras Importantes para Locação</CardTitle>
                            <CardDescription>
                                O que você deve saber antes de alugar um imóvel
                            </CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                                <div className="text-center">
                                    <div className="bg-blue-100 p-3 rounded-lg mb-3 mx-auto w-fit">
                                        <PiggyBank className="h-6 w-6 text-blue-600" />
                                    </div>
                                    <h3 className="font-semibold mb-2">Regra dos 30%</h3>
                                    <p className="text-sm text-muted-foreground">
                                        O aluguel não deve passar de 30% da sua renda líquida
                                    </p>
                                </div>
                                <div className="text-center">
                                    <div className="bg-green-100 p-3 rounded-lg mb-3 mx-auto w-fit">
                                        <CheckCircle className="h-6 w-6 text-green-600" />
                                    </div>
                                    <h3 className="font-semibold mb-2">Renda Comprovada</h3>
                                    <p className="text-sm text-muted-foreground">
                                        Tenha renda 3x maior que o valor do aluguel
                                    </p>
                                </div>
                                <div className="text-center">
                                    <div className="bg-yellow-100 p-3 rounded-lg mb-3 mx-auto w-fit">
                                        <AlertTriangle className="h-6 w-6 text-yellow-600" />
                                    </div>
                                    <h3 className="font-semibold mb-2">Reserva de Emergência</h3>
                                    <p className="text-sm text-muted-foreground">
                                        Mantenha reserva para 6 meses de aluguel
                                    </p>
                                </div>
                                <div className="text-center">
                                    <div className="bg-purple-100 p-3 rounded-lg mb-3 mx-auto w-fit">
                                        <Calculator className="h-6 w-6 text-purple-600" />
                                    </div>
                                    <h3 className="font-semibold mb-2">Custos Extras</h3>
                                    <p className="text-sm text-muted-foreground">
                                        Some condomínio, IPTU e taxas ao aluguel
                                    </p>
                                </div>
                            </div>
                        </CardContent>
                    </Card>
                </div>
            </div>
        </MainLayout>
    );
};

export default RentBudgetPage;