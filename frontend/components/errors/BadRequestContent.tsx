"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { AlertTriangle, FileX, RefreshCw, ArrowLeft, HomeIcon, MessageSquareX } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const BadRequestContent = () => {
    const [confusedState, setConfusedState] = useState<number>(0);
    const [errorMessages, setErrorMessages] = useState<string[]>([]);

    useEffect(() => {
        console.error("400 Error: Requisição inválida - Bad Request");

        const messages = [
            "Ué? Que diabos você mandou?",
            "Syntax error na vida real...",
            "404: Lógica não encontrada",
            "System.exe has stopped working",
            "Computador confuso.exe"
        ];

        const stateInterval = setInterval(() => {
            setConfusedState((prev) => (prev < 2 ? prev + 1 : 0));
        }, 1800);

        const messageInterval = setInterval(() => {
            const randomMessage = messages[Math.floor(Math.random() * messages.length)];
            setErrorMessages((prev) => {
                const newMessages = [...prev, randomMessage];
                return newMessages.slice(-3); // Keep only last 3 messages
            });
        }, 2000);

        return () => {
            clearInterval(stateInterval);
            clearInterval(messageInterval);
        };
    }, []);

    const getConfusedIcon = () => {
        switch (confusedState) {
            case 0:
                return <AlertTriangle className="h-20 w-20 text-red-500 animate-bounce" />;
            case 1:
                return <FileX className="h-20 w-20 text-orange-500 animate-pulse" />;
            default:
                return <MessageSquareX className="h-20 w-20 text-red-600 animate-spin" />;
        }
    };

    return (
        <MainLayout hideFooter>
            <div className="min-h-[calc(100vh-80px)] flex flex-col items-center justify-center bg-gradient-to-b from-red-50 to-orange-100 dark:from-gray-900 dark:to-red-900/20 p-4">
                <Card className="max-w-lg w-full p-6 bg-white dark:bg-gray-800 shadow-xl border-red-200 dark:border-red-700">
                    <div className="flex flex-col items-center text-center">
                        <div className="mb-4">
                            {getConfusedIcon()}
                        </div>

                        <h1 className="text-6xl font-bold mb-2 text-red-600 dark:text-red-400">400</h1>
                        <h2 className="text-2xl font-semibold mb-4 text-gray-700 dark:text-gray-300">
                            Que Bagunça! 🤯
                        </h2>

                        <p className="text-gray-600 dark:text-gray-400 mb-6">
                            Sua requisição veio meio... digamos... artística demais!
                            O servidor ficou tão confuso que começou a falar sozinho. 🤖💭
                        </p>

                        <div className="w-full mb-6 p-4 bg-red-50 dark:bg-red-900/20 rounded-lg border border-red-200 dark:border-red-700">
                            <p className="text-sm text-red-700 dark:text-red-300 mb-3">
                                🧠 O que o servidor está pensando:
                            </p>
                            <div className="bg-gray-100 dark:bg-gray-700 p-3 rounded font-mono text-xs space-y-1">
                                {errorMessages.map((message) => (
                                    <div key={message} className="text-red-500 animate-pulse"> {message}
                                    </div>
                                ))}
                                {errorMessages.length === 0 && (
                                    <div className="text-gray-500"> Processando confusão...
                                    </div>
                                )}
                                <div className="text-xs text-gray-500 mt-2 border-t pt-2">
                                    Servidor.exe - Status: Muito confuso 😵‍💫
                                </div>
                            </div>
                        </div>

                        <div className="space-y-4 w-full">
                            <div className="p-4 bg-orange-50 dark:bg-orange-900/20 rounded-lg">
                                <p className="text-sm text-gray-600 dark:text-gray-300 mb-3">
                                    🔧 Possíveis causas da confusão:
                                </p>
                                <div className="text-xs text-gray-500 dark:text-gray-400 space-y-1 text-left">
                                    <div className="flex items-center">
                                        <span className="mr-2">🎯</span>
                                        Você digitou algo muito criativo no formulário
                                    </div>
                                    <div className="flex items-center">
                                        <span className="mr-2">📝</span>
                                        Faltou algum campo obrigatório
                                    </div>
                                    <div className="flex items-center">
                                        <span className="mr-2">🎪</span>
                                        Você inventou um formato de data novo
                                    </div>
                                    <div className="flex items-center">
                                        <span className="mr-2">🎨</span>
                                        Colocou emojis onde não deveria
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
                                    <RefreshCw className="h-4 w-4 mr-2" />
                                    Tentar de Novo
                                </Button>
                                <Link href="/" className="flex-1">
                                    <Button className="w-full bg-red-500 hover:bg-red-600">
                                        <HomeIcon className="h-4 w-4 mr-2" />
                                        Recomeçar
                                    </Button>
                                </Link>
                            </div>
                        </div>
                    </div>
                </Card>

                <div className="mt-8 text-center">
                    <p className="text-gray-600 dark:text-gray-400 text-sm">
                        💡 Dica: Tente ser mais gentil com os formulários na próxima vez! 😄
                    </p>
                </div>
            </div>
        </MainLayout>
    );
};

export default BadRequestContent;