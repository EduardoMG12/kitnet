"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Router, Wifi, WifiOff, Activity, ArrowLeft, HomeIcon, RotateCcw } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const BadGatewayContent = () => {
    const [gatewayState, setGatewayState] = useState<number>(0);
    const [connectionPulse, setConnectionPulse] = useState<boolean>(true);
    const [serverMessages, setServerMessages] = useState<string[]>([]);

    useEffect(() => {
        console.error("502 Error: Bad Gateway - Comunicação entre servidores falhou");

        const messages = [
            "Servidor A: Alô? Alô?",
            "Servidor B: Não é você, sou eu...",
            "Gateway: Vocês dois param de brigar!",
            "Load Balancer: Eu desisto...",
            "DNS: Não foi culpa minha!"
        ];

        const stateInterval = setInterval(() => {
            setGatewayState((prev) => (prev < 2 ? prev + 1 : 0));
        }, 2000);

        const pulseInterval = setInterval(() => {
            setConnectionPulse((prev) => !prev);
        }, 1000);

        const messageInterval = setInterval(() => {
            const randomMessage = messages[Math.floor(Math.random() * messages.length)];
            setServerMessages((prev) => {
                const newMessages = [...prev, randomMessage];
                return newMessages.slice(-4);
            });
        }, 2500);

        return () => {
            clearInterval(stateInterval);
            clearInterval(pulseInterval);
            clearInterval(messageInterval);
        };
    }, []);

    const getGatewayIcon = () => {
        switch (gatewayState) {
            case 0:
                return <Router className="h-20 w-20 text-blue-500 animate-pulse" />;
            case 1:
                return connectionPulse ?
                    <Wifi className="h-20 w-20 text-green-500 animate-bounce" /> :
                    <WifiOff className="h-20 w-20 text-red-500 animate-bounce" />;
            default:
                return <Activity className="h-20 w-20 text-orange-500 animate-ping" />;
        }
    };

    return (
        <MainLayout hideFooter>
            <div className="min-h-[calc(100vh-80px)] flex flex-col items-center justify-center bg-gradient-to-b from-blue-50 to-purple-100 dark:from-gray-900 dark:to-blue-900/20 p-4">
                <Card className="max-w-lg w-full p-6 bg-white dark:bg-gray-800 shadow-xl border-blue-200 dark:border-blue-700">
                    <div className="flex flex-col items-center text-center">
                        <div className="mb-4">
                            {getGatewayIcon()}
                        </div>

                        <h1 className="text-6xl font-bold mb-2 text-blue-600 dark:text-blue-400">502</h1>
                        <h2 className="text-2xl font-semibold mb-4 text-gray-700 dark:text-gray-300">
                            Telefone Sem Fio! 📞
                        </h2>

                        <p className="text-gray-600 dark:text-gray-400 mb-6">
                            Os servidores estão jogando telefone sem fio e a mensagem se perdeu no caminho!
                            Parece que alguém desligou o cabo de rede... 🔌💔
                        </p>

                        <div className="w-full mb-6 p-4 bg-blue-50 dark:bg-blue-900/20 rounded-lg border border-blue-200 dark:border-blue-700">
                            <p className="text-sm text-blue-700 dark:text-blue-300 mb-3">
                                💬 Conversa entre servidores:
                            </p>
                            <div className="bg-gray-100 dark:bg-gray-700 p-3 rounded font-mono text-xs space-y-1 max-h-24 overflow-y-auto">
                                {serverMessages.map((message) => (
                                    <div key={message} className="text-blue-600 dark:text-blue-400">
                                        {message}
                                    </div>
                                ))}
                                {serverMessages.length === 0 && (
                                    <div className="text-gray-500 animate-pulse">
                                        Estabelecendo comunicação...
                                    </div>
                                )}
                                <div className="text-xs text-gray-500 mt-2 border-t pt-2">
                                    Status: Comunicação interrompida 📡❌
                                </div>
                            </div>
                        </div>

                        <div className="space-y-4 w-full">
                            <div className="p-4 bg-purple-50 dark:bg-purple-900/20 rounded-lg">
                                <p className="text-sm text-gray-600 dark:text-gray-300 mb-3">
                                    🔧 Possíveis problemas:
                                </p>
                                <div className="text-xs text-gray-500 dark:text-gray-400 space-y-1 text-left">
                                    <div className="flex items-center">
                                        <span className="mr-2">🌐</span>
                                        Gateway confuso com as rotas
                                    </div>
                                    <div className="flex items-center">
                                        <span className="mr-2">💻</span>
                                        Servidor de destino offline
                                    </div>
                                    <div className="flex items-center">
                                        <span className="mr-2">🔌</span>
                                        Cabo de rede virou espaguete
                                    </div>
                                    <div className="flex items-center">
                                        <span className="mr-2">🤖</span>
                                        Robôs em greve
                                    </div>
                                </div>
                            </div>

                            <div className="flex flex-col sm:flex-row gap-3 justify-center">
                                <Button
                                    onClick={() => window.history.back()}
                                    variant="outline"
                                    className="flex-1"
                                >
                                    <ArrowLeft className="h-4 w-4 mr-2" />
                                    Voltar
                                </Button>
                                <Button
                                    onClick={() => window.location.reload()}
                                    variant="secondary"
                                    className="flex-1"
                                >
                                    <RotateCcw className="h-4 w-4 mr-2" />
                                    Religar
                                </Button>
                                <Link href="/" className="flex-1">
                                    <Button className="w-full bg-blue-500 hover:bg-blue-600">
                                        <HomeIcon className="h-4 w-4 mr-2" />
                                        Linha Direta
                                    </Button>
                                </Link>
                            </div>
                        </div>
                    </div>
                </Card>

                <div className="mt-8 text-center">
                    <p className="text-gray-600 dark:text-gray-400 text-sm">
                        📱 Dica: Tente ligar mais tarde, talvez eles respondam! 📞
                    </p>
                </div>
            </div>
        </MainLayout>
    );
};

export default BadGatewayContent;