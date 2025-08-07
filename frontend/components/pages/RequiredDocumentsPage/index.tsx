import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, FileText, User, DollarSign } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const RequiredDocumentsPage = () => {
    return (
        <MainLayout>
            <div className="py-16 bg-gradient-to-b from-[#f9f4e8] to-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="mb-8">
                        <Link href="/guides" className="inline-flex items-center text-[#e56b4e] hover:underline mb-4">
                            <ArrowLeft className="mr-2 h-4 w-4" />
                            Voltar aos Guias
                        </Link>
                        <h1 className="text-4xl font-heading font-bold mb-4">Documentos Necessários</h1>
                        <p className="text-xl text-muted-foreground">
                            Lista completa de documentos para alugar um imóvel
                        </p>
                    </div>

                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                        <div className="lg:col-span-2 space-y-8">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <User className="h-5 w-5 text-[#e56b4e]" />
                                        Documentos Pessoais
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <div className="space-y-3">
                                        <div className="border-l-4 border-[#e56b4e] pl-4">
                                            <h3 className="font-semibold">RG e CPF</h3>
                                            <p className="text-muted-foreground">Originais e cópias de todos os locatários</p>
                                        </div>
                                        <div className="border-l-4 border-[#e56b4e] pl-4">
                                            <h3 className="font-semibold">Certidão de Nascimento ou Casamento</h3>
                                            <p className="text-muted-foreground">Estado civil e composição familiar</p>
                                        </div>
                                        <div className="border-l-4 border-[#e56b4e] pl-4">
                                            <h3 className="font-semibold">Comprovante de Residência</h3>
                                            <p className="text-muted-foreground">Atual, de até 90 dias</p>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <DollarSign className="h-5 w-5 text-[#e56b4e]" />
                                        Comprovação de Renda
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <h3 className="font-semibold">Para CLT:</h3>
                                    <ul className="space-y-2 text-muted-foreground ml-4">
                                        <li>• 3 últimos holerites</li>
                                        <li>• Carteira de trabalho</li>
                                        <li>• Declaração de imposto de renda</li>
                                        <li>• Extrato bancário (3 meses)</li>
                                    </ul>

                                    <h3 className="font-semibold mt-6">Para Autônomos:</h3>
                                    <ul className="space-y-2 text-muted-foreground ml-4">
                                        <li>• Declaração de imposto de renda</li>
                                        <li>• Extrato bancário (6 meses)</li>
                                        <li>• Comprovante de atividade profissional</li>
                                        <li>• Contratos de prestação de serviço</li>
                                    </ul>

                                    <h3 className="font-semibold mt-6">Para Empresários:</h3>
                                    <ul className="space-y-2 text-muted-foreground ml-4">
                                        <li>• Contrato social da empresa</li>
                                        <li>• Balanço patrimonial</li>
                                        <li>• Declaração de imposto de renda PF e PJ</li>
                                        <li>• Extrato bancário pessoal e da empresa</li>
                                    </ul>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>Referências</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <h3 className="font-semibold mb-3">Referências Pessoais:</h3>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• 2 referências pessoais (nome, telefone, endereço)</li>
                                        <li>• Referência de locação anterior (se houver)</li>
                                    </ul>

                                    <h3 className="font-semibold mt-6 mb-3">Referências Comerciais:</h3>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Referência bancária</li>
                                        <li>• Referência comercial (loja, prestador de serviço)</li>
                                    </ul>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <FileText className="h-5 w-5 text-[#e56b4e]" />
                                        Documentos do Fiador
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="mb-4">
                                        Se optar por fiador, ele também precisará apresentar:
                                    </p>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Todos os documentos pessoais</li>
                                        <li>• Comprovação de renda (min. 3x o valor do aluguel)</li>
                                        <li>• Escritura do imóvel próprio (livre de ônus)</li>
                                        <li>• IPTU do imóvel</li>
                                        <li>• Certidão negativa de débitos</li>
                                    </ul>
                                </CardContent>
                            </Card>
                        </div>

                        <div className="space-y-6">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Dicas Importantes</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <div className="p-3 bg-[#e56b4e]/10 rounded-lg">
                                        <p className="text-sm font-medium">💡 Dica</p>
                                        <p className="text-sm text-muted-foreground">
                                            Organize todos os documentos antes de procurar imóveis
                                        </p>
                                    </div>
                                    <div className="p-3 bg-[#e56b4e]/10 rounded-lg">
                                        <p className="text-sm font-medium">⚠️ Atenção</p>
                                        <p className="text-sm text-muted-foreground">
                                            Documentos com mais de 90 dias podem ser recusados
                                        </p>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Guias Relacionados</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <Link href="/guides/como-alugar" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Como alugar um imóvel
                                    </Link>
                                    <Link href="/guides/contrato-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Contratos de aluguel
                                    </Link>
                                    <Link href="/guides/vistoria" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Como fazer vistoria
                                    </Link>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Precisa de Ajuda?</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-sm text-muted-foreground mb-4">
                                        Te ajudamos com toda a documentação
                                    </p>
                                    <Button className="w-full">
                                        Falar com Especialista
                                    </Button>
                                </CardContent>
                            </Card>
                        </div>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

export default RequiredDocumentsPage;