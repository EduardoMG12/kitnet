import GatewayTimeoutContent from "@/components/errors/GatewayTimeoutContent";
import MainLayout from "@/components/layout/MainLayout";
import type { Metadata } from 'next';

export const metadata: Metadata = {
    title: "504 - Gateway Timeout | Kitnet",
    description: "Nosso servidor demorou muito para responder. Por favor, tente novamente em alguns instantes. Se o problema persistir, entre em contato com o suporte.",
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
        url: 'https://www.kitnet.com.br/errors/gateway-timeout', // Ajuste a URL se for diferente
        title: '504 - Gateway Timeout | Kitnet',
        description: 'Nosso servidor demorou muito para responder. Tente novamente mais tarde.',
        siteName: 'Kitnet',
        type: 'website',
        images: [
            {
                url: 'https://www.kitnet.com.br/images/kitnet-logo-error.png', // Use uma imagem de erro genérica ou específica
                width: 800,
                height: 600,
                alt: 'Kitnet - Gateway Timeout',
            },
        ],
    },
    twitter: {
        card: 'summary_large_image',
        title: '504 - Gateway Timeout | Kitnet',
        description: 'Nosso servidor demorou muito para responder. Tente novamente mais tarde.',
        creator: '@KitnetBR',
        site: '@KitnetBR',
        images: ['https://www.kitnet.com.br/images/kitnet-logo-error.png'],
    },
};



export default function GatewayTimeoutPage() {
    return (
        <MainLayout hideFooter>
            <GatewayTimeoutContent />
        </MainLayout>
    );
}

