'use client';

import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import MainLayout from "@/components/layout/MainLayout";
import { ArrowLeft, User, Phone, Mail, Eye, Check, X, Star, DollarSign, Calendar, MapPin } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import Link from "next/link";

export interface Proposal {
    id: string;
    tenantName: string;
    tenantPhoto: string;
    tenantAge: number;
    profession: string;
    monthlyIncome: number;
    creditScore: number;
    rating: number;
    reviewCount: number;
    phone: string;
    email: string;
    message: string;
    proposedRent: number;
    proposedStartDate: string;
    status: "pending" | "approved" | "rejected";
    verificationStatus: "verified" | "pending" | "incomplete";
    submittedAt: string;
    hasGuarantor: boolean;
    petOwner: boolean;
    documents: {
        rg: boolean;
        cpf: boolean;
        income: boolean;
        residence: boolean;
        photo: boolean;
    };
}

interface PropertyProposalsProps {
    proposals: Proposal[];
}

const PropertyProposalsPage: React.FC<PropertyProposalsProps> = ({ proposals }) => {
    const { toast } = useToast();
    const [selectedTab, setSelectedTab] = useState("pending");


    const handleProposalAction = (proposalId: string, action: "approve" | "reject") => {
        const proposal = proposals.find(p => p.id === proposalId);
        const actionText = action === "approve" ? "aprovada" : "rejeitada";

        toast({
            title: `Proposta ${actionText}`,
            description: `A proposta de ${proposal?.tenantName} foi ${actionText} com sucesso.`,
        });
    };

    const getStatusColor = (status: string) => {
        switch (status) {
            case "verified": return "bg-green-100 text-green-800";
            case "pending": return "bg-yellow-100 text-yellow-800";
            case "incomplete": return "bg-red-100 text-red-800";
            case "approved": return "bg-green-100 text-green-800";
            case "rejected": return "bg-red-100 text-red-800";
            default: return "bg-gray-100 text-gray-800";
        }
    };

    const getScoreColor = (score: number) => {
        if (score >= 700) return "text-green-600";
        if (score >= 600) return "text-yellow-600";
        return "text-red-600";
    };

    const filteredProposals = proposals.filter(proposal => {
        if (selectedTab === "pending") return proposal.status === "pending";
        if (selectedTab === "approved") return proposal.status === "approved";
        if (selectedTab === "rejected") return proposal.status === "rejected";
        return true;
    });

    const ProposalCard = ({ proposal }: { proposal: Proposal; }) => (
        <Card className="hover:shadow-md transition-shadow">
            <CardHeader className="pb-4">
                <div className="flex items-start justify-between">
                    <div className="flex items-center gap-4">
                        <img
                            src={proposal.tenantPhoto}
                            alt={proposal.tenantName}
                            className="w-16 h-16 rounded-full object-cover"
                        />
                        <div>
                            <CardTitle className="text-lg">{proposal.tenantName}</CardTitle>
                            <p className="text-sm text-gray-600">{proposal.profession}, {proposal.tenantAge} anos</p>
                            <div className="flex items-center gap-2 mt-1">
                                <div className="flex items-center">
                                    <Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                                    <span className="text-sm font-medium ml-1">{proposal.rating}</span>
                                    <span className="text-sm text-gray-500 ml-1">({proposal.reviewCount} avaliações)</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="flex flex-col items-end gap-2">
                        <Badge className={getStatusColor(proposal.verificationStatus)}>
                            {proposal.verificationStatus === "verified" ? "Verificado" :
                                proposal.verificationStatus === "pending" ? "Em análise" : "Incompleto"}
                        </Badge>
                        {proposal.status === "pending" && (
                            <Badge variant="outline">Aguardando resposta</Badge>
                        )}
                    </div>
                </div>
            </CardHeader>

            <CardContent className="space-y-4">
                <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                    <div>
                        <p className="text-sm text-gray-600">Renda Mensal</p>
                        <p className="font-semibold">R$ {proposal.monthlyIncome.toLocaleString()}</p>
                    </div>
                    <div>
                        <p className="text-sm text-gray-600">Score de Crédito</p>
                        <p className={`font-semibold ${getScoreColor(proposal.creditScore)}`}>
                            {proposal.creditScore}
                        </p>
                    </div>
                    <div>
                        <p className="text-sm text-gray-600">Proposta</p>
                        <p className="font-semibold">R$ {proposal.proposedRent.toLocaleString()}</p>
                    </div>
                    <div>
                        <p className="text-sm text-gray-600">Início</p>
                        <p className="font-semibold">{new Date(proposal.proposedStartDate).toLocaleDateString('pt-BR')}</p>
                    </div>
                </div>

                <div className="flex flex-wrap gap-2">
                    {proposal.hasGuarantor && (
                        <Badge variant="secondary">Com fiador</Badge>
                    )}
                    {proposal.petOwner && (
                        <Badge variant="secondary">Possui pet</Badge>
                    )}
                    <Badge variant="outline">
                        Docs: {Object.values(proposal.documents).filter(Boolean).length}/5
                    </Badge>
                </div>

                <div className="bg-gray-50 p-3 rounded-lg">
                    <p className="text-sm text-gray-700">"{proposal.message}"</p>
                </div>

                <div className="flex gap-2">
                    <Dialog>
                        <DialogTrigger asChild>
                            <Button variant="outline" className="flex-1">
                                <Eye className="h-4 w-4 mr-2" />
                                Ver Detalhes
                            </Button>
                        </DialogTrigger>
                        <DialogContent className="max-w-2xl max-h-[80vh] overflow-y-auto">
                            <DialogHeader>
                                <DialogTitle>Detalhes da Proposta - {proposal.tenantName}</DialogTitle>
                                <DialogDescription>
                                    Informações completas do candidato
                                </DialogDescription>
                            </DialogHeader>

                            <div className="space-y-6">
                                <div className="flex items-center gap-4">
                                    <img
                                        src={proposal.tenantPhoto}
                                        alt={proposal.tenantName}
                                        className="w-20 h-20 rounded-full object-cover"
                                    />
                                    <div>
                                        <h3 className="text-xl font-semibold">{proposal.tenantName}</h3>
                                        <p className="text-gray-600">{proposal.profession}, {proposal.tenantAge} anos</p>
                                        <div className="flex items-center gap-4 mt-2">
                                            <div className="flex items-center">
                                                <Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                                                <span className="ml-1">{proposal.rating} ({proposal.reviewCount} avaliações)</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div className="grid grid-cols-2 gap-4">
                                    <div>
                                        <h4 className="font-semibold mb-2">Informações Financeiras</h4>
                                        <div className="space-y-2 text-sm">
                                            <div className="flex justify-between">
                                                <span>Renda Mensal:</span>
                                                <span className="font-medium">R$ {proposal.monthlyIncome.toLocaleString()}</span>
                                            </div>
                                            <div className="flex justify-between">
                                                <span>Score de Crédito:</span>
                                                <span className={`font-medium ${getScoreColor(proposal.creditScore)}`}>
                                                    {proposal.creditScore}
                                                </span>
                                            </div>
                                            <div className="flex justify-between">
                                                <span>Proposta de Aluguel:</span>
                                                <span className="font-medium">R$ {proposal.proposedRent.toLocaleString()}</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div>
                                        <h4 className="font-semibold mb-2">Documentação</h4>
                                        <div className="space-y-2 text-sm">
                                            <div className="flex justify-between items-center">
                                                <span>RG:</span>
                                                {proposal.documents.rg ?
                                                    <Check className="h-4 w-4 text-green-600" /> :
                                                    <X className="h-4 w-4 text-red-600" />
                                                }
                                            </div>
                                            <div className="flex justify-between items-center">
                                                <span>CPF:</span>
                                                {proposal.documents.cpf ?
                                                    <Check className="h-4 w-4 text-green-600" /> :
                                                    <X className="h-4 w-4 text-red-600" />
                                                }
                                            </div>
                                            <div className="flex justify-between items-center">
                                                <span>Comprovante de Renda:</span>
                                                {proposal.documents.income ?
                                                    <Check className="h-4 w-4 text-green-600" /> :
                                                    <X className="h-4 w-4 text-red-600" />
                                                }
                                            </div>
                                            <div className="flex justify-between items-center">
                                                <span>Comprovante de Residência:</span>
                                                {proposal.documents.residence ?
                                                    <Check className="h-4 w-4 text-green-600" /> :
                                                    <X className="h-4 w-4 text-red-600" />
                                                }
                                            </div>
                                            <div className="flex justify-between items-center">
                                                <span>Foto:</span>
                                                {proposal.documents.photo ?
                                                    <Check className="h-4 w-4 text-green-600" /> :
                                                    <X className="h-4 w-4 text-red-600" />
                                                }
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div>
                                    <h4 className="font-semibold mb-2">Contato</h4>
                                    <div className="space-y-2 text-sm">
                                        <div className="flex items-center gap-2">
                                            <Phone className="h-4 w-4" />
                                            <span>{proposal.phone}</span>
                                        </div>
                                        <div className="flex items-center gap-2">
                                            <Mail className="h-4 w-4" />
                                            <span>{proposal.email}</span>
                                        </div>
                                    </div>
                                </div>

                                <div>
                                    <h4 className="font-semibold mb-2">Mensagem do Interessado</h4>
                                    <div className="bg-gray-50 p-4 rounded-lg">
                                        <p className="text-sm">"{proposal.message}"</p>
                                    </div>
                                </div>
                            </div>
                        </DialogContent>
                    </Dialog>

                    {proposal.status === "pending" && (
                        <>
                            <Button
                                onClick={() => handleProposalAction(proposal.id, "approve")}
                                className="bg-green-600 hover:bg-green-700"
                            >
                                <Check className="h-4 w-4 mr-2" />
                                Aprovar
                            </Button>
                            <Button
                                onClick={() => handleProposalAction(proposal.id, "reject")}
                                variant="destructive"
                            >
                                <X className="h-4 w-4 mr-2" />
                                Rejeitar
                            </Button>
                        </>
                    )}
                </div>
            </CardContent>
        </Card>
    );

    return (
        <MainLayout>
            <div className="min-h-screen bg-[#f9f4e8] py-8">
                <div className="container mx-auto px-4 max-w-6xl">
                    <div className="mb-6">
                        <Link href="/properties" className="inline-flex items-center text-[#e56b4e] hover:underline">
                            <ArrowLeft className="h-4 w-4 mr-2" />
                            Voltar para imóveis
                        </Link>
                    </div>

                    <Card className="mb-8">
                        <CardHeader className="text-center">
                            <CardTitle className="text-3xl font-heading text-[#2c3e50] flex items-center justify-center gap-2">
                                <User className="h-8 w-8 text-[#e56b4e]" />
                                Propostas Recebidas
                            </CardTitle>
                            <CardTitle className="text-lg text-gray-600">
                                Apartamento na Rua das Flores, 123 - Vila Madalena
                            </CardTitle>
                        </CardHeader>
                    </Card>

                    <Tabs value={selectedTab} onValueChange={setSelectedTab} className="space-y-6">
                        <TabsList className="grid w-full grid-cols-4">
                            <TabsTrigger value="all">
                                Todas ({proposals.length})
                            </TabsTrigger>
                            <TabsTrigger value="pending">
                                Pendentes ({proposals.filter(p => p.status === "pending").length})
                            </TabsTrigger>
                            <TabsTrigger value="approved">
                                Aprovadas ({proposals.filter(p => p.status === "approved").length})
                            </TabsTrigger>
                            <TabsTrigger value="rejected">
                                Rejeitadas ({proposals.filter(p => p.status === "rejected").length})
                            </TabsTrigger>
                        </TabsList>

                        <TabsContent value="all" className="space-y-6">
                            {proposals.map((proposal) => (
                                <ProposalCard key={proposal.id} proposal={proposal} />
                            ))}
                        </TabsContent>

                        <TabsContent value="pending" className="space-y-6">
                            {filteredProposals.map((proposal) => (
                                <ProposalCard key={proposal.id} proposal={proposal} />
                            ))}
                        </TabsContent>

                        <TabsContent value="approved" className="space-y-6">
                            {filteredProposals.map((proposal) => (
                                <ProposalCard key={proposal.id} proposal={proposal} />
                            ))}
                        </TabsContent>

                        <TabsContent value="rejected" className="space-y-6">
                            {filteredProposals.map((proposal) => (
                                <ProposalCard key={proposal.id} proposal={proposal} />
                            ))}
                        </TabsContent>
                    </Tabs>

                    {filteredProposals.length === 0 && (
                        <Card className="text-center py-12">
                            <CardContent>
                                <User className="h-16 w-16 mx-auto text-gray-400 mb-4" />
                                <h3 className="text-xl font-semibold text-gray-600 mb-2">
                                    Nenhuma proposta encontrada
                                </h3>
                                <p className="text-gray-500">
                                    {selectedTab === "pending" && "Não há propostas pendentes no momento."}
                                    {selectedTab === "approved" && "Não há propostas aprovadas ainda."}
                                    {selectedTab === "rejected" && "Não há propostas rejeitadas."}
                                </p>
                            </CardContent>
                        </Card>
                    )}
                </div>
            </div>
        </MainLayout>
    );
};

export default PropertyProposalsPage;
