import InternalServerErrorContent from "@/components/pages/errors/InternalServerErrorContent";
import MainLayout from "@/components/layout/MainLayout";
import type { Metadata } from 'next';

export const metadata: Metadata = {
    title: "500 - Erro Interno do Servidor | Kitnet",
    description: "Um erro inesperado ocorreu em nosso servidor. Nossos engenheiros foram notificados e estão trabalhando para resolver o problema. Por favor, tente novamente mais tarde.",
    // Configurações para evitar indexação por mecanismos de busca
    robots: {
        index: false,
        follow: false,
        nocache: true,
        googleBot: {
            index: false,
            follow: false,
            noimageindex: true,
            'max-video-preview': -1,
            'max-snippet': -1,
        },
    },
    openGraph: {
        url: 'https://www.kitnet.com.br/errors/internal-server-error', // Ajuste a URL se for diferente
        title: '500 - Erro Interno do Servidor | Kitnet',
        description: 'Um erro inesperado ocorreu em nosso servidor. Tente novamente mais tarde.',
        siteName: 'Kitnet',
        type: 'website',
        images: [
            {
                url: 'https://www.kitnet.com.br/images/kitnet-logo-error.png', // Use sua imagem de erro genérica
                width: 800,
                height: 600,
                alt: 'Kitnet - Erro Interno do Servidor',
            },
        ],
    },
    twitter: {
        card: 'summary_large_image',
        title: '500 - Erro Interno do Servidor | Kitnet',
        description: 'Um erro inesperado ocorreu em nosso servidor. Tente novamente mais tarde.',
        creator: '@KitnetBR',
        site: '@KitnetBR',
        images: ['https://www.kitnet.com.br/images/kitnet-logo-error.png'],
    },
};


export default function InternalServerErrorPage() {
    return (
        <InternalServerErrorContent />
    );
}

