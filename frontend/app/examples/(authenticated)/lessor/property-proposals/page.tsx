import Footer from '@/components/Footer';
import HeaderDefault from '@/components/headers/HeaderDefault';
import PropertyProposalsPage, { type Proposal } from '@/components/pages/PropertyProposalsPage';
import React from 'react';


// Mock data - seria vindo de uma API
const proposals: Proposal[] = [
    {
        id: "1",
        tenantName: "Ana Silva Santos",
        tenantPhoto: "/placeholder.svg",
        tenantAge: 28,
        profession: "Engenheira de Software",
        monthlyIncome: 12000,
        creditScore: 750,
        rating: 4.8,
        reviewCount: 12,
        phone: "(11) 99999-1234",
        email: "ana.silva@email.com",
        message: "Olá! Tenho muito interesse no imóvel. Sou profissional estável, não fumante e cuido muito bem dos lugares onde moro. Posso apresentar todas as documentações necessárias.",
        proposedRent: 2800,
        proposedStartDate: "2024-02-01",
        status: "pending",
        verificationStatus: "verified",
        submittedAt: "2024-01-15T10:30:00",
        hasGuarantor: true,
        petOwner: false,
        documents: {
            rg: true,
            cpf: true,
            income: true,
            residence: true,
            photo: true,
        },
    },
    {
        id: "2",
        tenantName: "Carlos Oliveira",
        tenantPhoto: "/placeholder.svg",
        tenantAge: 35,
        profession: "Designer Gráfico",
        monthlyIncome: 8000,
        creditScore: 680,
        rating: 4.5,
        reviewCount: 8,
        phone: "(11) 98888-5678",
        email: "carlos.design@email.com",
        message: "Boa tarde! Estou procurando um novo lar para mim e minha esposa. Somos pessoas organizadas e responsáveis. Tenho toda a documentação em dia.",
        proposedRent: 2700,
        proposedStartDate: "2024-01-20",
        status: "pending",
        verificationStatus: "pending",
        submittedAt: "2024-01-14T15:45:00",
        hasGuarantor: true,
        petOwner: true,
        documents: {
            rg: true,
            cpf: true,
            income: true,
            residence: false,
            photo: true,
        },
    },
    {
        id: "3",
        tenantName: "Mariana Costa",
        tenantPhoto: "/placeholder.svg",
        tenantAge: 31,
        profession: "Médica",
        monthlyIncome: 15000,
        creditScore: 820,
        rating: 5.0,
        reviewCount: 15,
        phone: "(11) 97777-9876",
        email: "dra.mariana@email.com",
        message: "Olá! Sou médica e preciso de um imóvel próximo ao hospital onde trabalho. Tenho excelente histórico de locação e todas as referências necessárias.",
        proposedRent: 3000,
        proposedStartDate: "2024-01-25",
        status: "approved",
        verificationStatus: "verified",
        submittedAt: "2024-01-13T09:15:00",
        hasGuarantor: false,
        petOwner: false,
        documents: {
            rg: true,
            cpf: true,
            income: true,
            residence: true,
            photo: true,
        },
    },
];

const PropertyProposal = () => {
    return (
        <>
            <HeaderDefault />
            <PropertyProposalsPage proposals={proposals} />
            <Footer />
        </>
    );
};

export default PropertyProposal;